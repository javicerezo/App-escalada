package classApp

data class Comment (
    var comment: String ?= null,
    var date: String ?= null,
    var difficultyOfRoute: String ?=null,
    var idEmailUser : String ?= null,
    var idNameRoute: String ?= null,
    var mettersOfRoute: Int ?=null,
    var nameUser: String ?=null,
    var sectorOfRoute: String ?= null
)
