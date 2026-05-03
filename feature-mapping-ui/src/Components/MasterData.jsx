import { useNavigate } from "react-router-dom";
import "../Stylling/MasterData.css";
import { getCount } from '../services/api';
import { useEffect, useState } from "react";

function MasterData() {

  const navigate = useNavigate();

  const [count , setCount] = useState({
    featureCount: 0,
    vehicleCount: 0,
    marketCount: 0,
    applicabilityCount: 0
  });

  useEffect(() => {
    getCount()
      .then((res) => {
        setCount(res.data); 
      })
      .catch((err) => console.log(err));
  }, []);

  return (
    <div className="dashboard-container">

      {/* HEADER */}
      <h2 className="dashboard-title">
        Master Data Dashboard
      </h2>

      {/* SUMMARY CARDS */}
      <div className="card-container">

        <div className="card">
          <h3>Features</h3>
          <p>{count.featureCount}</p>
        </div>

        <div className="card">
          <h3>Vehicles</h3>
          <p>{count.vehicleCount}</p>
        </div>

        <div className="card">
          <h3>Markets</h3>
          <p>{count.marketCount}</p>
        </div>

        <div className="card">
          <h3>Mappings</h3>
          <p>{count.applicabilityCount}</p>
        </div>

      </div>

      {/* QUICK ACTIONS */}
      <h3 className="section-title">
        Quick Actions
      </h3>

      <div className="action-buttons">

        <button
          className="btn btn-primary"
          onClick={() => navigate("/feature")}
        >
          + Add Feature
        </button>

        <button
          className="btn btn-primary"
          onClick={() => navigate("/vehicle")}
        >
          + Add Vehicle
        </button>

        <button
          className="btn btn-primary"
          onClick={() => navigate("/mapping")}
        >
          Go to Mapping
        </button>

      </div>

    </div>
  );
}

export default MasterData;