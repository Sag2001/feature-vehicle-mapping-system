import { useState } from "react";
import { getfeatures, createFeature, deleteFeature } from "../services/api";
import "../Stylling/Feature.css";
import { useNavigate } from "react-router-dom";

function FeaturesPage() {
    const nevigate = useNavigate(); 
    const [features, setFeatures] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [showTable, setShowTable] = useState(false);

    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [category, setCategory] = useState("");

    const [loading, setLoading] = useState(false);

    const [message, setMessage] = useState("");
    const [messageType, setMessageType] = useState(""); // success | error

    // ================= GET FEATURES =================
    const handleGetFeatures = async () => {
        try {
            const res = await getfeatures();
            setFeatures(res.data);
            setShowTable(true);
            setMessage("");
        } catch (err) {
            console.log(err);
            setMessage("Failed to load features");
            setMessageType("error");
        }
    };

    // ================= ADD FEATURE =================
    const handleAdd = async () => {

        if (!name || !category) {
            setMessage("Please fill required fields");
            setMessageType("error");
            return;
        }

        setLoading(true);

        const payload = { name, description, category };

        try {
            await createFeature(payload);

            setName("");
            setDescription("");
            setCategory("");
            setShowForm(false);

            setMessage("Feature saved successfully!");
            setMessageType("success");

            // refresh table if visible
            if (showTable) {
                const res = await getfeatures();
                setFeatures(res.data);
            }

        } catch (err) {
            console.log(err);

            if (err.response?.status === 409) {
                setMessage("Feature already exists!");
            } else {
                setMessage("Failed to create feature");
            }

            setMessageType("error");

        } finally {
            setLoading(false);
        }
    };

    // ================= DELETE FEATURE =================
    const handleDelete = async (id) => {

        const confirmDelete = window.confirm(
            "Are you sure you want to delete this feature?"
        );

        if (!confirmDelete) return;

        try {
            await deleteFeature(id);

            setFeatures((prev) => prev.filter((f) => f.id !== id));

            setMessage("Feature deleted successfully!");
            setMessageType("success");

        } catch (err) {
            console.log(err);

            // 🔴 SAFE extraction (always string)
            let errorMsg =
                err?.response?.data?.message ||
                err?.response?.data ||
                err?.message ||
                "";

            // convert object → string safely
            if (typeof errorMsg === "object") {
                errorMsg = JSON.stringify(errorMsg);
            }

            const msg = errorMsg.toString().toLowerCase();

            if (
                msg.includes("foreign key") ||
                msg.includes("mapped") ||
                msg.includes("cannot delete") ||
                msg.includes("constraint fails")
            ) {
                setMessage("❌ This feature is already mapped and cannot be deleted!");
            } else {
                setMessage("❌ Server error while deleting feature!");
            }

            setMessageType("error");
        }
    };

    return (
        <div className="features-container">

            <h2>Features Module</h2>

            {/* MESSAGE */}
            {message && (
                <div
                    className={`message-box ${messageType === "success"
                        ? "message-success"
                        : "message-error"
                        }`}
                >
                    {message}
                </div>
            )}

            {/* BUTTONS */}
            <div className="button-group">

                <button
                    className="btn btn-primary"
                    onClick={() => setShowForm(!showForm)}
                >
                    + Add Feature
                </button>

                <button
                    className="btn btn-secondary"
                    onClick={handleGetFeatures}
                >
                    Get Features
                </button>

                <button 
                className="btn btn-home"
                onClick={()=> nevigate("/")}
                
                >
                    Home
                </button>

            </div>

            {/* FORM */}
            {showForm && (
                <div className="form-box">

                    <input
                        placeholder="Feature Name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />

                    <input
                        placeholder="Description"
                        value={description}
                        onChange={(e) => setDescription(e.target.value)}
                    />

                    <select
                        value={category}
                        onChange={(e) => setCategory(e.target.value)}
                    >
                        <option value="">Select Category</option>
                        <option value="Safety">Safety</option>
                        <option value="Infotainment">Infotainment</option>
                        <option value="Driver Assistance">Driver Assistance</option>
                        <option value="Connectivity">Connectivity</option>
                    </select>

                    <button
                        className="btn btn-save"
                        onClick={handleAdd}
                        disabled={loading}
                    >
                        {loading ? "Saving..." : "Save Feature"}
                    </button>

                </div>
            )}

            {/* TABLE */}
            {showTable && (
                <div className="table-container">

                    <table>

                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Category</th>
                                <th>Actions</th>
                            </tr>
                        </thead>

                        <tbody>
                            {features.length > 0 ? (
                                features.map((f) => (
                                    <tr key={f.id}>
                                        <td>{f.id}</td>
                                        <td>{f.name}</td>
                                        <td>{f.description}</td>
                                        <td>{f.category}</td>

                                        <td>
                                            <button
                                                className="btn btn-delete"
                                                onClick={() => handleDelete(f.id)}
                                            >
                                                Delete
                                            </button>
                                        </td>

                                    </tr>
                                ))
                            ) : (
                                <tr>
                                    <td colSpan="5" className="no-data">
                                        No features found
                                    </td>
                                </tr>
                            )}
                        </tbody>

                    </table>

                </div>
            )}

        </div>
    );
}

export default FeaturesPage;