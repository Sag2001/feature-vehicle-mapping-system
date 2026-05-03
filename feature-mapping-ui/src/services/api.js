import axios from "axios";

const API = axios.create({
     baseURL: "http://localhost:8082"
}
);

//Feature
export const getfeatures = () => API.get("/feature");

//DeleteFeatureById
export const deleteFeature = (id) => API.delete(`/feature/${id}`);

//Post Mapping for feature
export const createFeature = (payload)=> API.post("/feature",payload);

//Vehicle
export const getVehicles = () => API.get("/vehicle");

//Post Mapping for Vehicle
export const createVehicle = (payload)=> API.post("/vehicle",payload);

//DeleteFeatureById
export const deleteVehicle = (id) => API.delete(`/vehicle/${id}`);

// Markets
export const getMarkets = () => API.get("/market");

//Post Mapping
export const createMapping = (payload)=> API.post("/Applicability",payload);

//Get Mapping
export const getMappings = () => API.get("/Applicability");

//Delete Maping
export const deleteMapping = (id) => API.delete(`/Applicability/${id}`);

//DashBourdCount 
export const getCount = ()=>API.get("dashboard/count");

