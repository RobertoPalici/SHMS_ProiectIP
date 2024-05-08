import React, { useState } from 'react';
import Nav from './components/header/Nav';
import Product from './components/product/Product';
import image_1 from './components/pictures/image 3.png';
import image_2 from './components/pictures/background-overlay.png';
import image_3 from './components/pictures/vector.png';
import footer from './components/pictures/Footer.png';
import POTATOES from './components/pictures/POTATOES.png';
import CABBAGES from './components/pictures/CABBAGES.png';
import CUCUMBERS from './components/pictures/CUCUMBERS.png';
import TOMATOES from './components/pictures/TOMATOES.png'; 
import HOT_PEPPERS from './components/pictures/HOT PEPPERS.png';
import './App.css';

const App: React.FC = () => {
  const [isTagDropdownOpen, setTagDropdownOpen] = useState(false);
  const [isSortDropdownOpen, setSortDropdownOpen] = useState(false);

  const toggleTagDropdown = () => {
    setTagDropdownOpen(!isTagDropdownOpen);
    if (isSortDropdownOpen) {
      setSortDropdownOpen(false);
    }
  };

  const products = [
    { name: ' TOMATOES ', imageSrc: TOMATOES, initialQuantity: 5 },
    { name: ' HOT PEPPERS', imageSrc: HOT_PEPPERS, initialQuantity: 3 },
    { name: ' POTATOES ', imageSrc: POTATOES, initialQuantity: 8 },
    { name: ' CUCUMBERS ', imageSrc: CUCUMBERS, initialQuantity: 8 },
    { name: ' CABBAGES ', imageSrc: CABBAGES, initialQuantity: 8 },
  ];

  const toggleSortDropdown = () => {
    setSortDropdownOpen(!isSortDropdownOpen);
    if (isTagDropdownOpen) {
      setTagDropdownOpen(false);
    }
  };

  return (
    <div className="appContainer">
      <Nav />
      <img className="appImage" src={image_1} alt="Imagine 1" />
      <img className="overlay" src={image_2} alt="Overlay" />
      <img className="vector" src={image_3} alt="Vector" />
      
      <div className="shopping-list">Shopping List</div>
     
      <div className="buttonContainer">
        {/*<button onClick={toggleTagDropdown} className="tagButton">Tag</button>
        {isTagDropdownOpen && (
          <div className="dropdowntag">
            <button> Food</button>
            <button>Others</button>
          </div>
        )}*/}
        {/*<button onClick={toggleSortDropdown} className="sortButton">Sort</button>
        {isSortDropdownOpen && (
          <div className="dropdownsort">
            <button>Quantity</button>
            <button>Date of add</button>
            <button>A..Z</button>
            <button>Z..A</button>
          </div>
        )}*/}
        <div className="nav-links"></div>
        <button className="restockSuggestions">Restock Suggestions</button>
      </div>

      <div className="product-list">
        {products.map((product, index) => (
          <div key={index} className="product-container">
            <Product
              name={product.name}
              imageSrc={product.imageSrc}
              initialQuantity={product.initialQuantity}
            />
          </div>
        ))}
      </div>

      <img className="footer" src={footer} alt="Footer" />
    </div>
  );
};

export default App;
