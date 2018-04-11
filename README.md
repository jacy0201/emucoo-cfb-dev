# king-micro-service
#资源头部
@RestController
@RequestMapping("/xxxx")

增
@RequestMapping(method = RequestMethod.POST, value = "/", consumes = "application/json")
删除
@RequestMapping(method = RequestMethod.DELETE,value = "/{id}")
改
@RequestMapping(method = RequestMethod.PUT,value = "/", consumes = "application/json")
查
@RequestMapping(method = RequestMethod.GET, value = "/list")
@RequestMapping(method = RequestMethod.GET, value = "/")