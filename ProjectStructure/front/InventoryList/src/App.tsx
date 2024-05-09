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
import APIRequest from './APIRequest';

const App: React.FC = () => {

  const API_URL = 'http://localhost:8081/inventory';
  //const API_URL = 'http://localhost:5000/itemList';

  const [products, setProducts] = useState<ProductProps>({itemList: []});
  const [newProduct, setNewProduct] = useState('');
  const [newQuantity, setNewQuantity] = useState(0);
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      try{
        const response = await fetch(API_URL);
        if(!response.ok) throw Error('Data was not received');
        const listItems = await response.json();

        setProducts(prevProducts => {
          return {...prevProducts, itemList: listItems.itemList};
        });
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          } else {
            setFetchError('An unknown error occurred');
          }
      } finally{
        setLoading(false);
      }
    }
    setTimeout(() => {
      (async () => await fetchItems())();
    }, 2000);
  }, [])

  return (
    <div className="appContainer">
      <Nav />
      <main>
        {loading && <p>Inventory is loading...</p>}
        {fetchError && <p style={{color: "red"}}>{`Error: ${fetchError}`}</p>}
        {!fetchError && !loading && <Content 
          products = {products}
          setProducts = {setProducts}
          newProduct = {newProduct}
          setNewProduct = {setNewProduct}
          newQuantity = {newQuantity}
          setNewQuantity = {setNewQuantity}
          fetchError = {fetchError}
          setFetchError = {setFetchError}
        />}
      </main>
      <img className="footer" src={footer} alt="Footer" />
    </div>
  );
};

export default App;
