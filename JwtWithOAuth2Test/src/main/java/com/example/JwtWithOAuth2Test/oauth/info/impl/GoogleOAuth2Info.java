package com.example.JwtWithOAuth2Test.oauth.info.impl;

import com.example.JwtWithOAuth2Test.oauth.info.OAuth2UserInfo;

import java.util.Map;

public class GoogleOAuth2Info extends OAuth2UserInfo {

    public GoogleOAuth2Info(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}
