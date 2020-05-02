package domain.issue

sealed trait Error

object OriginEstimateIsLessThanNewEstimate extends Error
