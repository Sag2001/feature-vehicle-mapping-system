import { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  getVehicles,
  createVehicle,
  deleteVehicle
} from "../services/api";
import "../Stylling/Feature.css";

function VehiclePage() {

  const navigate = useNavigate();

  const [vehicles, setVehicles] = useState([]);
  const [showForm, setShowForm] = useState(false);
  const [showTable, setShowTable] = useState(false);

  const [name, setName] = useState("");
  const [platform, setPlatform] = useState("");
  const [variant, setVariant] = useState("");

  const [loading, setLoading] = useState(false);

  const [message, setMessage] = useState("");
  const [messageType, setMessageType] = useState("");

  // GET VEHICLES
  const handleGetVehicles = async () => {
    try {
      const res = await getVehicles();
      setVehicles(res.data);
      setShowTable(true);
      setMessage("");
    } catch (err) {
      console.log(err);
      setMessage("Failed to load vehicle details");
      setMessageType("error");
    }
  };

  // ADD VEHICLE
  const handleAdd = async () => {

    if (!name || !platform || !variant) {
      setMessage("Please fill required fields");
      setMessageType("error");
      return;
    }

    setLoading(true);

    const payload = {
      name,
      platform,
      variant
    };

    try {
      await createVehicle(payload);

      setName("");
      setPlatform("");
      setVariant("");
      setShowForm(false);

      setMessage("Vehicle details saved successfully!");
      setMessageType("success");

      if (showTable) {
        const res = await getVehicles();
        setVehicles(res.data);
      }

    } catch (err) {
      console.log(err);

      if (err.response?.status === 409) {
        setMessage("Vehicle already exists!");
      } else {
        setMessage("Failed to create vehicle details");
      }

      setMessageType("error");

    } finally {
      setLoading(false);
    }
  };

  // DELETE VEHICLE
  const handleDelete = async (id) => {

    const confirmDelete = window.confirm(
      "Are you sure you want to delete this vehicle?"
    );

    if (!confirmDelete) return;

    try {
      await deleteVehicle(id);

      setVehicles((prev) => prev.filter((v) => v.id !== id));

      setMessage("Vehicle deleted successfully!");
      setMessageType("success");

    } catch (err) {
      console.log(err);

      const msg = err?.response?.data?.toString().toLowerCase() || "";

      if (
        msg.includes("mapping") ||
        msg.includes("foreign key") ||
        msg.includes("constraint") ||
        msg.includes("used")
      ) {
        setMessage("❌ Cannot delete: Vehicle is used in mapping table!");
      } else {
        setMessage("❌ Failed to delete vehicle!");
      }

      setMessageType("error");
    }
  };

  return (
    <div className="features-container">

      <h2>Vehicle Module</h2>

      {/* MESSAGE */}
      {message && (
        <div
          className={`message-box ${
            messageType === "success"
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
          + Add Vehicle
        </button>

        <button
          className="btn btn-secondary"
          onClick={handleGetVehicles}
        >
          Get Vehicles
        </button>

        {/* 🏠 HOME BUTTON */}
        <button
          className="btn btn-home"
          onClick={() => navigate("/")}
        >
          Home
        </button>

      </div>

      {/* FORM */}
      {showForm && (
        <div className="form-box">

          <input
            placeholder="Vehicle Name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />

          <input
            placeholder="Platform"
            value={platform}
            onChange={(e) => setPlatform(e.target.value)}
          />

          <input
            placeholder="Variant"
            value={variant}
            onChange={(e) => setVariant(e.target.value)}
          />

          <button
            className="btn btn-save"
            onClick={handleAdd}
            disabled={loading}
          >
            {loading ? "Saving..." : "Save Vehicle"}
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
                <th>Platform</th>
                <th>Variant</th>
                <th>Action</th>
              </tr>
            </thead>

            <tbody>
              {vehicles.length > 0 ? (
                vehicles.map((v) => (
                  <tr key={v.id}>
                    <td>{v.id}</td>
                    <td>{v.name}</td>
                    <td>{v.platform}</td>
                    <td>{v.variant}</td>
                    <td>
                      <button
                        className="btn btn-delete"
                        onClick={() => handleDelete(v.id)}
                      >
                        Delete
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="5" className="no-data">
                    No Vehicle found
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

export default VehiclePage;