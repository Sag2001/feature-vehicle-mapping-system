import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import MasterData from "./Components/MasterData";
import MappingPage from "./Pages/MappingPage";
import FeaturesPage from "./Pages/FeaturesPage";
import VehiclePage from "./Pages/VehiclePage";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<MasterData />} />
        <Route path="/mapping" element={<MappingPage />} />
        <Route path="/feature" element = {<FeaturesPage/>}/>
        <Route path="/vehicle" element = {<VehiclePage/>}/>
      </Routes>
    </Router>
  );
}

export default App;