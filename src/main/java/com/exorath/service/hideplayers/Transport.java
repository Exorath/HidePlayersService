/*
 * Copyright 2017 Exorath
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.exorath.service.hideplayers;

import com.exorath.service.commons.portProvider.PortProvider;
import com.exorath.service.hideplayers.res.VisibilityPlayer;
import com.google.gson.Gson;
import spark.Route;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.put;

/**
 * Created by toonsev on 5/9/2017.
 */
public class Transport {

    private static final Gson GSON = new Gson();

    public static void setup(Service service, PortProvider portProvider) {
        port(portProvider.getPort());

        get("/players/:uuid", getGetPlayersRoute(service), GSON::toJson);
        put("/players/:uuid", getPutPlayersRoute(service), GSON::toJson);
    }



    private static Route getGetPlayersRoute(Service service) {
        return (req, res) -> service.getVisibilityPlayer(req.params("uuid"));
    }

    private static Route getPutPlayersRoute(Service service) {
        return (req, res) -> service.setVisibilityPlayer(req.params("uuid"), GSON.fromJson(req.body(), VisibilityPlayer.class));
    }

}
