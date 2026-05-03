import { useEffect, useState } from "react";
import { getVehicles, getMarkets, createMapping } from '../services/api';
import "../Stylling/Mapping.css";

function CreateMappingUI({ selectedFeatureId, onSuccess, onBack }) {

    const [selectVehicle, setSelectVehicle] = useState("");
    const [selectMarket, setSelectMarket] = useState("");
    const [selectStatus, setSelectStatus] = useState("");

    const [vehicles, setVehicles] = useState([]);
    const [markets, setMarkets] = useState([]);

    const [loading, setLoading] = useState(false); // ✅ NEW
    const [error, setError] = useState(""); // ✅ NEW

    useEffect(() => {
        loadData();
    }, []);

    const loadData = async () => {
        try {
            const [vehicleRes, marketRes] = await Promise.all([
                getVehicles(),
                getMarkets()
            ]);

            setVehicles(vehicleRes.data);
            setMarkets(marketRes.data);
        }
        catch (e) {
            console.log("API Error", e);
            setError("Failed to load data");
        }
    };

    const handleClick = async () => {
        if (!selectVehicle || !selectMarket || !selectStatus) {
            setError("⚠️ Please fill all fields"); // ✅ no alert
            return;
        }

        setError("");
        setLoading(true);

        const Payload = {
            featureId: Number(selectedFeatureId),
            vehicleModelId: Number(selectVehicle),
            marketId: Number(selectMarket),
            status: selectStatus.toUpperCase().replaceAll(" ", "_")
        };

        try {
            const res = await createMapping(Payload);
            console.log("Success:", res.data);

            // ✅ send success back to parent
            onSuccess("✅ Mapping created successfully!");

        } catch (err) {
  console.error("POST error:", err);

  if (err.response?.status === 409) {
    setError("⚠️ This mapping already exists. Please update it instead.");
  } else {
    setError("❌ Something went wrong. Please try again.");
  }
} finally {
            setLoading(false);
        }
    };

    return (
        <div className="mapping-container">

            <h3>Mapping Form</h3>

            {/* ✅ Error message */}
            {error && <div className="error-text">{error}</div>}

            <div className="form-group">
                <label>Vehicle Model:</label>
                <select
                    value={selectVehicle}
                    onChange={(e) => setSelectVehicle(e.target.value)}
                >
                    <option value="">Select Vehicle</option>
                    {vehicles.map((v) => (
                        <option key={v.id} value={v.id}>
                            {v.name}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-group">
                <label>Market:</label>
                <select
                    value={selectMarket}
                    onChange={(e) => setSelectMarket(e.target.value)}
                >
                    <option value="">Select Market</option>
                    {markets.map((m) => (
                        <option key={m.id} value={m.id}>
                            {m.name}
                        </option>
                    ))}
                </select>
            </div>

            <div className="form-group">
                <label>Status:</label>
                <select
                    value={selectStatus}
                    onChange={(e) => setSelectStatus(e.target.value)}
                >
                    <option value="">Select Status</option>
                    <option value="Applicable">Applicable</option>
                    <option value="Not Applicable">Not Applicable</option>
                    <option value="Under Review">Under Review</option>
                </select>
            </div>

            {/* Buttons */}
            <div style={{ display: "flex", gap: "10px", marginTop: "10px" }}>
                
                <button 
                    className="submit-btn" 
                    onClick={handleClick}
                    disabled={loading}
                >
                    {loading ? "Saving..." : "Submit"}
                </button>

                <button 
                    className="submit-btn" 
                    onClick={onBack}
                    style={{ background: "#ccc", color: "#333" }}
                >
                    Back
                </button>

            </div>

        </div>
    );
}

export default CreateMappingUI;