package com.ivan.prokofyev.handh_test.data.model;

import com.google.auto.value.AutoValue;

/**
 * Created by Shiraha on 7/13/2017.
 */
@AutoValue
public abstract class User {
    public abstract String name();
    public abstract String email();
    public abstract String password();

    public static Builder builder(){return new AutoValue_User.Builder();}

    @AutoValue.Builder
    public abstract static class Builder{
        public abstract Builder setName(String name);
        public abstract Builder setEmail(String email);
        public abstract Builder setPassword(String password);
        public abstract User build();
    }
}
