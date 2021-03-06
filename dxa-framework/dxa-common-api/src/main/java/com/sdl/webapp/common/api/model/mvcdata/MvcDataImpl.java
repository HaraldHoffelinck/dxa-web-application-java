package com.sdl.webapp.common.api.model.mvcdata;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sdl.webapp.common.api.model.MvcData;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter(AccessLevel.PROTECTED)
@Accessors(chain = true)
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class MvcDataImpl implements MvcData {
    @JsonProperty("ControllerAreaName")
    private String controllerAreaName;

    @JsonProperty("ControllerName")
    private String controllerName;

    @JsonProperty("ActionName")
    private String actionName;

    @JsonProperty("AreaName")
    private String areaName;

    @JsonProperty("ViewName")
    private String viewName;

    @JsonProperty("RegionAreaName")
    private String regionAreaName;

    @JsonProperty("RegionName")
    private String regionName;

    @JsonIgnore
    private Map<String, String> routeValues = new HashMap<>();

    @JsonIgnore
    private Map<String, Object> metadata = new HashMap<>();

    /**
     * <p>Constructor for MvcDataImpl.</p>
     */
    protected MvcDataImpl() {
    }

    /**
     * <p>Constructor for MvcDataImpl.</p>
     *
     * @param mvcData a {@link com.sdl.webapp.common.api.model.MvcData} object.
     */
    protected MvcDataImpl(MvcData mvcData) {
        this.controllerAreaName = mvcData.getControllerAreaName();
        this.controllerName = mvcData.getControllerName();
        this.actionName = mvcData.getActionName();
        this.areaName = mvcData.getAreaName();
        this.viewName = mvcData.getViewName();
        this.regionAreaName = mvcData.getRegionAreaName();
        this.regionName = mvcData.getRegionName();
        this.routeValues = mvcData.getRouteValues();
        this.metadata = mvcData.getMetadata();
    }

    public static MvcDataImplBuilder newBuilder() {
        return new MvcDataImplBuilder();
    }

    public MvcDataImplBuilder toBuilder() {
        return MvcDataImplBuilder.toBuilder(this);
    }

    /**
     * Builder for MvcData. Lombok's implementation fails on Javadoc.
     */
    public static class MvcDataImplBuilder {
        private String controllerAreaName;
        private String controllerName;
        private String actionName;
        private String areaName;
        private String viewName;
        private String regionAreaName;
        private String regionName;
        private Map<String, String> routeValues = new HashMap<>();
        private Map<String, Object> metadata = new HashMap<>();

        protected static MvcDataImplBuilder toBuilder(MvcData mvcData) {
            return (new MvcDataImplBuilder())
                    .controllerAreaName(mvcData.getControllerAreaName())
                    .controllerName(mvcData.getControllerName())
                    .actionName(mvcData.getActionName())
                    .areaName(mvcData.getAreaName())
                    .viewName(mvcData.getViewName())
                    .regionAreaName(mvcData.getRegionAreaName())
                    .regionName(mvcData.getRegionName())
                    .routeValues(mvcData.getRouteValues())
                    .metadata(mvcData.getMetadata());
        }

        public MvcDataImplBuilder controllerAreaName(String controllerAreaName) {
            this.controllerAreaName = controllerAreaName;
            return this;
        }

        public MvcDataImplBuilder controllerName(String controllerName) {
            this.controllerName = controllerName;
            return this;
        }

        public MvcDataImplBuilder actionName(String actionName) {
            this.actionName = actionName;
            return this;
        }

        public MvcDataImplBuilder areaName(String areaName) {
            this.areaName = areaName;
            return this;
        }

        public MvcDataImplBuilder viewName(String viewName) {
            this.viewName = viewName;
            return this;
        }

        public MvcDataImplBuilder regionAreaName(String regionAreaName) {
            this.regionAreaName = regionAreaName;
            return this;
        }

        public MvcDataImplBuilder regionName(String regionName) {
            this.regionName = regionName;
            return this;
        }

        public MvcDataImplBuilder routeValues(Map<String, String> routeValues) {
            this.routeValues = routeValues;
            return this;
        }

        public MvcDataImplBuilder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public MvcDataImpl build() {
            return new MvcDataImpl(controllerAreaName, controllerName, actionName, areaName, viewName, regionAreaName, regionName, routeValues, metadata);
        }
    }
}
