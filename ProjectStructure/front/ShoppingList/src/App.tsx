import React, { useState, useEffect } from 'react';
import Nav from './components/header/Nav';
import {ProductProps} from './components/product/Product';
import footer from './components/pictures/Footer.png';
import './App.css';
import Content from './Content';

const App: React.FC = () => {

  const API_URL = 'http://localhost:8081/shopping';

  const [products, setProducts] = useState<ProductProps>({shoppingLists: [{shoppingList: [], listName: ''}]});
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
        console.log(listItems);
        console.log(listItems.shoppingLists);
        console.log(listItems.shoppingLists[0].shoppingList);
        console.log(listItems.shoppingLists[0]);
        console.log(listItems.shoppingLists[1]);

        /*setProducts(prevProducts => {
          const newList = listItems.shoppingLists[0].shoppingList;
          const updatedShoppingLists = [...prevProducts.shoppingLists, { shoppingList: newList }];
          return { ...prevProducts, shoppingLists: updatedShoppingLists };
        });*/
        setProducts(prevProducts => {
          console.log('setam');
          console.log(prevProducts);
          const updatedShoppingLists = listItems.shoppingLists.map((shoppingList: any, index: number) => {
            const newList = listItems.shoppingLists[index].shoppingList;
            const newName = listItems.shoppingLists[index].name;
            console.log(newList);
            return { shoppingList: newList, listName: newName };
          });
          console.log(updatedShoppingLists)
          return { ...prevProducts, shoppingLists: updatedShoppingLists };
        });
        
        
        console.log(products);
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          } else {
            setFetchError('An unknown error occurred');
          }
      } {
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
        {loading && <p>Shopping lists are loading...</p>}
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
