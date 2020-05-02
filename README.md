# hub-board-2
api on local server, and command line interface?

## requirements
+ store template issue
  + and select "create" or "skip" on planning
+ create same attrs issue by cut a issue
+ create same attrs issues by crunch a issue
+ assign issue to preseted members
+ create issues for multi members

## resource and commands
### issue create ( no idempotency )
```
$ issue create -t 'send message' -b '+ [ ] implement\n+ [ ] review' -l 'feature' -a 'John' -p 'today' -m 'sprint 2' -e 3
```

options

option | type              | constraint | default            
:--    | :--               | :--        | :--                
-t     | string            | require    | -                  
-b     | string            |            | no body            
-l     | string [, string] |            | no labels          
-a     | string [, string] |            | no assignees       
-p     | string            |            | left side          
-m     | string            |            | current milestone  
-e     | int               |            | 0                  

### issue copy ( no idempotency )
```
$ issue copy -n 3 -t 'review'
```

options

option | type              | constraint | default            
:--    | :--               | :--        | :--                
-n     | int               | require    | -                  
-t     | string            | require    | -                  
-b     | string            |            | no body            
-l     | string [, string] |            | same labels        
-a     | string [, string] |            | same assignees     
-p     | string            |            | same pipeline      
-m     | string            |            | same milestone     
-e     | int               |            | same estimate      

### issue cut ( no idempotency )
```
$ issue cut -n 3 -t 'review' -a 'Jack'
```

options

option | type              | constraint | default            
:--    | :--               | :--        | :--                
-n     | int               | require    | -                  
-t     | string            | require    | -                  
-b     | string            |            | no body            
-l     | string [, string] |            | same labels        
-a     | string [, string] |            | same assignees     
-p     | string            |            | same pipeline      
-m     | string            |            | same milestone     
-e     | int               |            | 0                  

+ create new issue, then comment `cut from #x` to new issue.
+ close origin issue if origin issue's estimate became zero.
+ failure if origin issue's estimate is less than new issue's estimate.

### issue crunch ( no idempotency )
```
$ issue crunch -n 3 -bt 'send message' | -t 'implement' -e 1 | -t 'test' -e 1 | -t 'review' -e 1
```

options

option | type     | constraint         | default          
:--    | :--      | :--                | :--              
-n     | int      | require            | -                
-bt    | string   |                    | no base title    

options for issues separated with `|`

option | type              | constraint | default            
:--    | :--               | :--        | :--                
-t     | string            | require    | -                  
-b     | string            |            | no body            
-l     | string [, string] |            | same labels        
-a     | string [, string] |            | same assignees     
-p     | string            |            | same pipeline      
-m     | string            |            | same milestone     
-e     | int               |            | 0                  

+ create new issues, then comment `crunched from #x` to new issues.
+ close origin issue if origin issue's estimate became zero.

### issue assign ( idempotency )
```
$ issue assign -n 3 -a 'Jane'
```

option | type     | constraint | default          
:--    | :--      | :--        | :--              
-n     | int      | require    | -                
-a     | string   | require    | -

### milestone create ( idempotency )
```
$ milestone create -n 'sprint 2' -s '2020/04/01' -e '2020/04/08'
```

options

option | type   | constraint | default
:--    | :--    | :--        | :--    
-n     | string | require    | -      
-s     | string | require    | -      
-e     | string | require    | -      

