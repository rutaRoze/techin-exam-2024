import axios from 'axios';
import { useState } from 'react';
import './CategoryPage.css';
import Config from '../config/Config.js';
import ModalSuccess from '../modal/ModalSuccess.jsx';
import { useNavigate } from 'react-router-dom';

function CategoryRegistration() {
  const navigate = useNavigate();
  const [showModal, setShowModal] = useState(false);
  const [formData, setFormData] = useState({
    name: ''
  });

  const handleForm = (event) => {
    setFormData((previousFormData) => ({
      ...previousFormData,
      [event.target.name]: event.target.value
    }));
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    sendDataToBackend();
  };

  const closeModal = () => {
    setShowModal(false);
    navigate("/category")
  };

  const sendDataToBackend = () => {
    const url = Config.apiDomain + Config.endpoints.category.create;
    axios
      .post(url, {
        name: formData.name
      })
      .then((response) => {
        setShowModal(true)
      })
      .catch((error) => console.log(error));
  };

  return (
    <>
      <div className="container-fluid background-form ">
        <div className="row justify-content-center">
          <form
            onSubmit={handleSubmit}
            className=" col-8 col-sm-8 col-md-6 col-xl-4 mt-3"
          >
            <fieldset>
              <legend className="mt-5 my-2 fw-medium" style={{ color: 'black', fontWeight: '200' }}>
                Register New Category
              </legend>
              <div className="mb-3">
                <input
                  type="text"
                  className="form-control"
                  placeholder="Enter category name"
                  name="name"
                  value={formData.name}
                  onChange={handleForm}
                />
              </div>
              <button type="submit" className="create-category-button">
                Create
              </button>
            </fieldset>
          </form>
        </div>
      </div>
      <ModalSuccess show={showModal} handleClose={closeModal} customMessage={'Category created successfully'} />
    </>
  );
}

export default CategoryRegistration;
