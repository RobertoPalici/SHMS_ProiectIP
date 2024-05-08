import React, { useState, useEffect } from 'react';
import Nav from './components/header/Nav';
import Product, { ItemList } from './components/product/Product';
import {ProductProps} from './components/product/Product';
import image_1 from './components/pictures/image 3.png';
import image_2 from './components/pictures/background-overlay.png';
import image_3 from './components/pictures/vector.png';
import footer from './components/pictures/Footer.png';
import POTATOES from './components/pictures/POTATOES.png';
import CABBAGES from './components/pictures/CABBAGES.png';
import CUCUMBERS from './components/pictures/CUCUMBERS.png';
import TOMATOES from './components/pictures/TOMATOES.png'; 
import HOT_PEPPERS from './components/pictures/HOT PEPPERS.png';
import questionmark from './components/pictures/questionmark.png';
import './App.css';
import Content from './Content'; 

const App: React.FC = () => {
  
  /*const savedData = localStorage.getItem('inventoryList');
  const initialProducts = savedData ? JSON.parse(savedData) : [];*/

  const [products, setProducts] = useState<ProductProps>({
    "itemList": []
  });
  const[newProduct, setNewProduct] = useState('');
  const[newQuantity, setNewQuantity] = useState(0);

  useEffect(() => {
    localStorage.setItem('inventoryList', JSON.stringify(products));
  }, [products])

  return (
    <div className="appContainer">
      <Nav />
      <Content 
        products = {products}
        setProducts = {setProducts}
        newProduct = {newProduct}
        setNewProduct = {setNewProduct}
        newQuantity = {newQuantity}
        setNewQuantity = {setNewQuantity}
      />
      <img className="footer" src={footer} alt="Footer" />
    </div>
  );
};

export default App;
