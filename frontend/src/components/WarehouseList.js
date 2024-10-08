import React, { useState, useEffect } from 'react';
import WarehouseService from '../services/warehouseService';
import { useNavigate } from 'react-router-dom';
import UtilizationBar from './UtilizationBar';

const WarehouseList = () => {
  const [warehouses, setWarehouses] = useState([]);
  const [sortCriteria, setSortCriteria] = useState('name'); // Default is to sort by name
  const navigate = useNavigate();

  useEffect(() => { // Fetch all warehouses on component mount
    WarehouseService.getAll().then(response => {
      setWarehouses(response.data);
    });
  }, []);

  const handleEdit = (warehouseId) => { // Navigate to edit warehouse page with warehouse ID given
    navigate(`/edit-warehouse/${warehouseId}`);
  };

  const handleDelete = (warehouseId) => { // Deletes warehouse and updates state
    if (window.confirm('This will delete the warehouse and all items contained within it.\n\nAre you sure you want to delete this warehouse?')) { // Confirm deletion popup done here
      WarehouseService.delete(warehouseId).then(() => { // Delete warehouse and update state, note that items are deleted via cascade in the backend
        setWarehouses(warehouses.filter(warehouse => warehouse.id !== warehouseId));
      });
    }
  };

  const handleSortChange = (e) => { // Update sort criteria state based on user selection
    setSortCriteria(e.target.value);
  };

  const sortedWarehouses = [...warehouses].sort((a, b) => { // Sort warehouses based on sort criteria, in this case, by name or utilization
    if (sortCriteria === 'name') {
      return a.name.localeCompare(b.name);
    } else if (sortCriteria === 'utilization') {
      const utilizationA = (a.usedCapacity / a.capacity) * 100;
      const utilizationB = (b.usedCapacity / b.capacity) * 100;
      return utilizationB - utilizationA;
    }
    return 0;
  });

  return (
    <div>
      <h2>Warehouses</h2>
      <div className="mb-3">
        <label htmlFor="sortCriteria" className="form-label">Sort By:</label>
        <select id="sortCriteria" className="form-select" onChange={handleSortChange} value={sortCriteria}>
          <option value="name">Name (Alphabetical)</option>
          <option value="utilization">Utilization (%)</option>
        </select>
      </div>
      <div className="row">
        {sortedWarehouses.map(warehouse => (
          <div key={warehouse.id} className="col-md-6">
            <div className="card">
              <h5>{warehouse.name}</h5>
              <p>{warehouse.usedCapacity} / {warehouse.capacity} cubic feet utilized</p>
              <UtilizationBar usedCapacity={warehouse.usedCapacity} capacity={warehouse.capacity} />
              <div className="d-flex justify-content-between mt-3">
                <button onClick={() => handleEdit(warehouse.id)} className="btn btn-primary">Edit</button>
                <button onClick={() => handleDelete(warehouse.id)} className="btn btn-danger">Delete</button>
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default WarehouseList;
