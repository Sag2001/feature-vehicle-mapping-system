import { useEffect, useState } from "react";
import { getfeatures, getMappings, deleteMapping } from "../services/api";
import CreateMappingUI from "../Components/MappingUi";
import "../Stylling/Table.css";
import { useNavigate } from "react-router-dom";

function MappingPage() {

    const navigate = useNavigate();

    const [feature, setFeature] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState("");
    const [selectFeatureId, setSelectFeatureId] = useState(null);

    const [showMapping, setShowMapping] = useState(false);
    const [showMappingDetails, setShowMappingDetails] = useState(false);

    const [mappings, setMappings] = useState([]);
    const [message, setMessage] = useState("");

    // Pagination
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const size = 10;

    // GET FEATURES
    useEffect(() => {
        getfeatures()
            .then((res) => setFeature(res.data))
            .catch(() => setMessage("❌ Failed to load features"));
    }, []);

    // FILTER FEATURES
    const filterFeatures =
        selectedCategory === ""
            ? []
            : selectedCategory === "All"
                ? feature
                : feature.filter((f) => f.category === selectedCategory);

    // MAP FEATURE BUTTON
    const handleMapFeature = () => {
        if (!selectFeatureId) {
            setMessage("⚠️ Please select a feature first");
            return;
        }

        setShowMapping(true);
        setShowMappingDetails(false);
        setMessage("");
    };

    // DELETE MAPPING
    const handleDeleteMapping = async (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this mapping?"
        );

        if (!confirmDelete) return;

        try {
            await deleteMapping(id);

            setMappings((prev) => prev.filter((m) => m.id !== id));
            setMessage("✅ Mapping deleted successfully!");

        } catch (err) {
            console.log(err);
            setMessage("❌ Failed to delete mapping");
        }
    };

    // GET MAPPING DETAILS (PAGINATION)
    const handleGetMappings = async (pageNumber = 0) => {

        if (pageNumber < 0) return;

        try {
            const res = await getMappings(pageNumber, size);

            console.log("MAPPING API RESPONSE 👉", res.data);

            setMappings(res.data.content);
            setTotalPages(res.data.totalPages);
            setPage(res.data.number);

            setShowMappingDetails(true);
            setShowMapping(false);
            setMessage("");

        } catch (err) {
            console.log(err);
            setMessage("❌ Failed to load mapping details");
        }
    };

    return (
        <div className="page-wrapper">

            {/* ================= LIST VIEW ================= */}
            {!showMapping && !showMappingDetails && (
                <div className="page-container">

                    <h2 style={{ textAlign: "center", marginBottom: "20px" }}>
                        Mapping Module
                    </h2>

                    {/* MESSAGE */}
                    {message && (
                        <div className="success-banner">
                            {message}
                        </div>
                    )}

                    {/* TOOLBAR */}
                    <div className="toolbar">

                        <div className="form-group">
                            <label>Category</label>

                            <select
                                value={selectedCategory}
                                onChange={(e) => {
                                    setSelectedCategory(e.target.value);
                                    setSelectFeatureId(null);
                                    setMessage("");
                                }}
                            >
                                <option value="">Select Category</option>
                                <option value="All">All</option>
                                <option value="Safety">Safety</option>
                                <option value="Infotainment">Infotainment</option>
                                <option value="Driver Assistance">Driver Assistance</option>
                                <option value="Connectivity">Connectivity</option>
                            </select>
                        </div>

                        <button
                            className="map-btn"
                            onClick={handleMapFeature}
                            disabled={!selectFeatureId}
                        >
                            Map Feature
                        </button>

                        <button
                            className="map-btn"
                            onClick={() => handleGetMappings(0)}
                        >
                            Get Mapping Details
                        </button>

                    </div>

                    {/* EMPTY STATE */}
                    {selectedCategory === "" ? (
                        <p style={{ textAlign: "center", marginTop: "20px", color: "#635353" }}>
                            Please select a category to view features
                        </p>
                    ) : (
                        <div className="table-container">

                            <table>

                                <thead>
                                    <tr>
                                        <th>Select</th>
                                        <th>Name</th>
                                        <th>Category</th>
                                        <th>Description</th>
                                    </tr>
                                </thead>

                                <tbody>
                                    {filterFeatures.length > 0 ? (
                                        filterFeatures.map((f) => (
                                            <tr key={f.id}>
                                                <td>
                                                    <input
                                                        type="radio"
                                                        name="featureSelect"
                                                        onChange={() => setSelectFeatureId(f.id)}
                                                    />
                                                </td>
                                                <td>{f.name}</td>
                                                <td>{f.category}</td>
                                                <td>{f.description}</td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="4" style={{ textAlign: "center" }}>
                                                No features found
                                            </td>
                                        </tr>
                                    )}
                                </tbody>

                            </table>

                        </div>
                    )}

                    {/* HOME BUTTON */}
                    <button
                        className="btn btn-home"
                        onClick={() => navigate("/")}
                    >
                        Home
                    </button>

                </div>
            )}

            {/* ================= CREATE MAPPING VIEW ================= */}
            {showMapping && (
                <div className="page-container">

                    <h2 style={{ textAlign: "center" }}>
                        Create Mapping
                    </h2>

                    <CreateMappingUI
                        selectedFeatureId={selectFeatureId}
                        onSuccess={(msg) => {
                            setMessage(msg);
                            setShowMapping(false);
                            setSelectFeatureId(null);
                        }}
                        onBack={() => {
                            setShowMapping(false);
                            setMessage("");
                        }}
                    />

                </div>
            )}

            {/* ================= MAPPING DETAILS VIEW ================= */}
            {showMappingDetails && (
                <div className="page-container">

                    <h2 style={{ textAlign: "center", marginBottom: "20px" }}>
                        Mapping Details
                    </h2>

                    <div className="table-container">

                        <table>

                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Feature</th>
                                    <th>Vehicle</th>
                                    <th>Market</th>
                                    <th>Status</th>
                                    <th>Action</th>
                                </tr>
                            </thead>

                            <tbody>
                                {mappings.length > 0 ? (
                                    mappings.map((m) => (
                                        <tr key={m.id}>
                                            <td>{m.id}</td>
                                            <td>{m.featureName}</td>
                                            <td>{m.vehicleName}</td>
                                            <td>{m.marketName}</td>
                                            <td>{m.status}</td>
                                            <td>
                                                <button
                                                    className="btn btn-delete"
                                                    onClick={() => handleDeleteMapping(m.id)}
                                                >
                                                    Delete
                                                </button>
                                            </td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="6" style={{ textAlign: "center" }}>
                                            No mappings found
                                        </td>
                                    </tr>
                                )}
                            </tbody>

                        </table>

                    </div>

                    {/* ================= PAGINATION ================= */}
                    <div className="pagination">

                        <button
                            onClick={() => handleGetMappings(page - 1)}
                            disabled={page === 0}
                        >
                            Prev
                        </button>

                        {Array.from({ length: totalPages }, (_, i) => (
                            <button
                                key={i}
                                className={page === i ? "active" : ""}
                                onClick={() => handleGetMappings(i)}
                            >
                                {i + 1}
                            </button>
                        ))}

                        <button
                            onClick={() => handleGetMappings(page + 1)}
                            disabled={page === totalPages - 1}
                        >
                            Next
                        </button>

                    </div>

                    <button
                        className="btn btn-home"
                        onClick={() => {
                            setShowMappingDetails(false);
                            setMessage("");
                        }}
                    >
                        Back
                    </button>

                </div>
            )}

        </div>
    );
}

export default MappingPage;