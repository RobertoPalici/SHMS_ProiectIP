import React, { useState, useEffect } from 'react';
import Nav from './components/header/Nav';
import {ProductProps} from './components/product/Product';
import footer from './components/pictures/Footer.png';
import './App.css';
import Content from './Content';
import Notifications from './components/Notifications';

const App: React.FC = () => {

  const API_URL = 'http://localhost:8081/inventory';
  const API_URL_NOTIFICATIONS = 'http://localhost:8081/notifications';

  const [products, setProducts] = useState<ProductProps>({itemList: []});
  const [newProduct, setNewProduct] = useState('');
  const [newQuantity, setNewQuantity] = useState(0);
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  const clearNotifications = async () => {
    try {
      const response = await fetch(`${API_URL_NOTIFICATIONS}/clearNotifications`, {
        method: 'DELETE'
      });
      if (!response.ok) throw Error('Notifications could not be cleared');
    } catch (error) {
      console.error('Error clearing notifications:', error);
    }
  };

  useEffect(() => {
    const fetchItems = async () => {
      try{
        const response = await fetch(API_URL);
        if(!response.ok) throw Error('Data was not received');
        const listItems = await response.json();

        const notifications = await fetch(API_URL_NOTIFICATIONS);
        if (!notifications.ok) throw Error('Notifications were not received');
        const listNotifications = await notifications.json();

        setTimeout(() => {
          clearNotifications();
        }, 3000);


        console.log(listItems);
        console.log(listItems.itemList);

        setProducts(prevProducts => {
          return {...prevProducts, itemList: listItems.itemList};
        });
        console.log(products);
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

  const handleSort = async (filter: number) => {
      try{
        const response = await fetch(`${API_URL}/sortItems?sortFilter=${filter}`);
        if(!response.ok) throw Error('Data was not received');
        const listItems = await response.json();
        console.log(listItems);
        console.log(listItems.itemList);

        setProducts(prevProducts => {
          return {...prevProducts, itemList: listItems.itemList};
        });
        console.log(products);
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          }else {
            setFetchError('An unknown error occurred');
          }
      }
  }
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
          handleSort={handleSort}
        />}
        <Notifications /> { }
      </main>
      <img className="footer" src={footer} alt="Footer" />
    </div>
  );
};

export default App;
