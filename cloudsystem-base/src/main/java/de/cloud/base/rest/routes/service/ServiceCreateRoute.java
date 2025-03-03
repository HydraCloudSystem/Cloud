package de.cloud.base.rest.routes.service;

import de.cloud.api.CloudAPI;
import de.cloud.base.Base;
import spark.Request;
import spark.Response;
import spark.Route;

public class ServiceCreateRoute implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        if (!"POST".equalsIgnoreCase(request.requestMethod())) {
            response.status(405); // 405 = Method Not Allowed
            return "Error: Only POST requests are allowed for this endpoint.";
        }

        String groupName = request.queryParams("groupName");
        if (groupName == null || groupName.isEmpty()) {
            response.status(400); // 400 = Bad Request
            return "Error: 'groupName' parameter is required and cannot be empty.";
        }

        var serviceGroupOptional = CloudAPI.getInstance().getGroupManager().getServiceGroup(groupName);
        if (!serviceGroupOptional.isEmpty()) {
            if (serviceGroupOptional.get(0).getMaxOnlineService() >= CloudAPI.getInstance().getServiceManager().getAllServicesByGroup(serviceGroupOptional.get(0)).size()){
                response.status(404); // 404 = Not Found
                return "Error: Maximum online service count reached.";
            }

            var service = Base.getInstance().getServiceManager().createNewService(serviceGroupOptional.get(0).getName());
            return "Success: Service '" + service + "' has been created successfully.";
        } else {
            response.status(404); // 404 = Not Found
            return "Error: ServiceGroup '" + serviceGroupOptional + "' does not exist.";
        }
    }
}
