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

package com.exorath.service.hideplayers.api;

import com.exorath.service.hideplayers.Service;
import com.exorath.service.hideplayers.res.Success;
import com.exorath.service.hideplayers.res.VisibilityPlayer;
import com.exorath.service.hideplayers.res.VisibleState;
import com.google.gson.Gson;
import com.mashape.unirest.http.Unirest;

/**
 * Created by toonsev on 5/9/2017.
 */
public class HidePlayersServiceAPI implements Service {
    private static final Gson GSON = new Gson();
    private String address;

    public HidePlayersServiceAPI(String address) {
        this.address = address;
    }


    @Override
    public VisibilityPlayer getVisibilityPlayer(String uuid) {
        try {
            return GSON.fromJson(Unirest.get(url("/players/{uuid}"))
                    .routeParam("uuid", uuid).asString().getBody(), VisibilityPlayer.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Success setVisibilityPlayer(String uuid, VisibilityPlayer player) {
        try {
            return GSON.fromJson(Unirest.put(url("/players/{uuid}"))
                    .routeParam("uuid", uuid).body(GSON.toJson(player)).asString().getBody(), Success.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new Success(false, -1, e.getMessage());
        }
    }

    private String url(String endpoint) {
        return address + endpoint;
    }
}
