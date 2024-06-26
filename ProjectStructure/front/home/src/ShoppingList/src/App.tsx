import React, { useState, useEffect } from 'react';
import {Header} from './Components/Header/Header';
import {ProductProps} from './Components/Product/Product';
import footer from '../public/img/ico/Footer.png';
import './App.css';
import Content from './Components/Content/Content';
import Notifications from './Components/Notifications/Notifications';

const App: React.FC = () => {
  console.log('React version:', React.version, 'from', require.resolve('react'));

  const API_URL = 'http://localhost:8081/shopping';

  const [products, setProducts] = useState<ProductProps>({shoppingLists: [{shoppingList: [], listName: ''}]});
  const [newId, setNewId] = useState(0);
  const [newProduct, setNewProduct] = useState('');
  const [newQuantity, setNewQuantity] = useState(0);
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchItems = async () => {
      try{
        const response = await fetch(API_URL);
        //if(!response.ok) {//throw Error('Data was not received');
          const listItems = await response.json();

        setProducts(prevProducts => {
          const updatedShoppingLists = listItems.shoppingLists.map((shoppingList: any, index: number) => {
            const newList = listItems.shoppingLists[index].shoppingList;
            const newName = listItems.shoppingLists[index].name;
            return { shoppingList: newList, listName: newName };
          });
          return { ...prevProducts, shoppingLists: updatedShoppingLists };
        });
        
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          } else {
            setFetchError('An unknown error occurred');
          }
          console.log('Fetch error');
      } {
        setLoading(false);
      }
    }
    setTimeout(() => {
      (async () => await fetchItems())();
    }, 2000);
  }, [])

  return (
    <div className="appContainerShopping">
      <Header />
      <main>
        {loading && <p className='paragraphShopping'>Shopping lists are loading...</p>}
        {/*{fetchError && <p className='paragraphShopping' style={{color: "red"}}>{`Error: ${fetchError}`}</p>}*/}
        {/*!fetchError &&*/ !loading && 
          <Content 
            products = {products}
            newId = {newId}
            setNewId = {setNewId}
            setProducts = {setProducts}
            newProduct = {newProduct}
            setNewProduct = {setNewProduct}
            newQuantity = {newQuantity}
            setNewQuantity = {setNewQuantity}
            fetchError = {fetchError}
            setFetchError = {setFetchError}
          />
        }
        <Notifications />
      </main>
      <img className="footerShopping" src={footer} alt="Footer" />
    </div>
  );
};

export default App;
