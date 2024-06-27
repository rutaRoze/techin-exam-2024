import axios from 'axios';
import { useEffect, useState } from 'react';
import Config from '../config/Config.js';

function CategoryList() {
  const [categoryList, setCategoryList] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [search, setSearch] = useState('');
  const [editingCategory, setEditingCategory] = useState(null);
  const [newCategoryName, setNewCategoryName] = useState('');

  const getCategoryList = () => {
    const url = Config.apiDomain + Config.endpoints.category.getAll;

    axios
      .get(url)
      .then((response) => {
        setCategoryList(response.data);
        setIsLoading(false);
      })
      .catch((error) => console.log(error));
  };

  useEffect(() => {
    getCategoryList();
  }, []);

  const updateCategory = (id, newName) => {
    const url = `${Config.apiDomain}${Config.endpoints.category.update.replace('{id}', id)}`;

    axios
      .put(url, { name: newName })
      .then(() => {
        getCategoryList();
        exitEditMode();
      })
      .catch((error) => console.error(error));
  };

  const enterEditMode = (category) => {
    setEditingCategory(category);
    setNewCategoryName(category.name);
  };

  const exitEditMode = () => {
    setEditingCategory(null);
    setNewCategoryName('');
  };

  const handleInputChange = (event) => {
    setNewCategoryName(event.target.value);
  };

  const deleteCategory = (id) => {
    const url = `${Config.apiDomain}${Config.endpoints.category.delete.replace('{id}', id)}`;

    axios
      .delete(url)
      .then(() => {
        getCategoryList();
      })
      .catch((error) => console.error(error));
  };

  return (
    <>
      {isLoading && 'Data is loading...'}

      <h3 className="mt-5 fw-bolder">Category List</h3>
      <div className="container ">
        <form>
          <div className="my-5">
            <input
              type="text"
              onChange={(event) => setSearch(event.target.value)}
              className="form-control"
              placeholder="Search for category"
            />
          </div>
        </form>

        <table className="table table-striped table-hover mt-5 ">
          <thead>
          <tr>
            <th scope="col">ID</th>
            <th scope="col">Category Name</th>
            <th scope="col">Manage Category</th>
          </tr>
          </thead>
          <tbody>
          {categoryList
            .filter((category) => {
              return search.toLowerCase() === ''
                ? category
                : category.name.toLowerCase().includes(search);
            })
            .map((category) => (
              <tr key={category.id}>
                <th scope="row">{category.id}</th>
                <td>
                  {editingCategory === category ? (
                    <input
                      type="text"
                      value={newCategoryName}
                      onChange={handleInputChange}
                      className="form-control"
                    />
                  ) : (
                    category.name
                  )}
                </td>
                <td>
                  {editingCategory === category ? (
                    <>
                      <button
                        type="button"
                        onClick={() =>
                          updateCategory(category.id, newCategoryName)
                        }
                        className="btn btn-success me-2"
                      >
                        Save
                      </button>
                      <button
                        type="button"
                        onClick={exitEditMode}
                        className="btn btn-secondary me-2"
                      >
                        Cancel
                      </button>
                    </>
                  ) : (
                    <button
                      type="button"
                      onClick={() => enterEditMode(category)}
                      className="btn btn-warning me-2"
                    >
                      Update
                    </button>
                  )}
                  <button
                    type="button"
                    // disabled={category.isDeleting}
                    onClick={() => deleteCategory(category.id)}
                    className="btn btn-danger"
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </>
  );
}

export default CategoryList;
