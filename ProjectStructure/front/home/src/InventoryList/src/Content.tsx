import React, { useState } from 'react';
import {ProductProps , ItemList} from './components/product/Product';
import image_1 from './components/pictures/image 3.png';
import ItemLists from './ItemLists';
import APIRequest from './APIRequest';
import './App.css';

interface ContentProps{
  products: ProductProps;
  newId: number;
  newProduct: string;
  newQuantity: number;
  setProducts: React.Dispatch<React.SetStateAction<ProductProps>>;
  setNewId: React.Dispatch<React.SetStateAction<number>>;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
  setFetchError: React.Dispatch<any>;
  handleSort: (filter: number) => void;
}

const Content: React.FC<ContentProps> = ({
  handleSort, 
  products, 
  setProducts,
  newId,
  setNewId, 
  newProduct, 
  setNewProduct, 
  newQuantity, 
  setNewQuantity,  
  setFetchError}) => {
 
  const [isTag, setTag] = useState(false);
  const [isSort, setSort] = useState(false);
  const [updatedQuantity, setUpdatedQuantity] = useState(0);

  const API_URL = 'http://localhost:8081/inventory';

  const saveProducts = (newProducts : ItemList[]) => {
    setProducts(prevProducts => {
      return {...prevProducts, itemList: newProducts};  
    });
  }

  const handleIncreaseQuantity = async (name : string) =>{
    if(products.itemList!==undefined){
      const listItems = products.itemList.map((item) => item.item.name === name && item.quantity.value >= 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value + 1}, averageConsumption: item.averageConsumption} : item); 
      saveProducts(listItems);

      const targetProduct = listItems.filter((item) => item.item.name === name);
      const index = products.itemList.findIndex(item => item.item.name === name);
      const options = {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          item: {
            name: targetProduct[0].item.name,
            eatable: targetProduct[0].item.eatable
          },
          quantity: {
            value: targetProduct[0].quantity.value,
            type: targetProduct[0].quantity.type 
          },
          averageConsumption: targetProduct[0].averageConsumption
        })
      }
      const response = await APIRequest(`${API_URL}/changeQuantity?id=${index}&quantity=${targetProduct[0].quantity.value}`, options);
      if(response)
        setFetchError(response);
    }
  }

  const handleDecreaseQuantity = async (name : string) =>{
    if(products.itemList !== undefined){
      const listItems = products.itemList.map((item) => item.item.name === name && item.quantity.value > 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value - 1}, averageConsumption: item.averageConsumption} : item); 
      saveProducts(listItems);

      const targetProduct = listItems.filter((item) => item.item.name === name);
      const index = products.itemList.findIndex(item => item.item.name === name);
      const options = {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          item: {
            name: targetProduct[0].item.name,
            eatable: targetProduct[0].item.eatable
          },
          quantity: {
            value: targetProduct[0].quantity.value,
            type: targetProduct[0].quantity.type 
          },
          averageConsumption: targetProduct[0].averageConsumption
        })
      }
      const response = await APIRequest(`${API_URL}/changeQuantity?id=${index}&quantity=${targetProduct[0].quantity.value}`, options);    
      if(response)
        setFetchError(response);
    }
  }

  const handleChangeQuantity = async (name: string, updatedQuantity: number) =>{
    if(products.itemList !== undefined){
      const listItems = products.itemList.map((item) => item.item.name === name && item.quantity.value >= 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: updatedQuantity}, averageConsumption: item.averageConsumption} : item); 
      saveProducts(listItems);

      const targetProduct = listItems.filter((item) => item.item.name === name);
      const index = products.itemList.findIndex(item => item.item.name === name);
      const options = {
        method: 'PATCH',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          item: {
            name: targetProduct[0].item.name,
            eatable: targetProduct[0].item.eatable
          },
          quantity: {
            value: targetProduct[0].quantity.value,
            type: targetProduct[0].quantity.type 
          },
          averageConsumption: targetProduct[0].averageConsumption
        })
      }
      const response = await APIRequest(`${API_URL}/changeQuantity?id=${index}&quantity=${targetProduct[0].quantity.value}`, options);    
      if(response)
        setFetchError(response);
    }
  }

  const handleSubmitUpdate = (e : React.FormEvent<HTMLFormElement>, name: string) => {
    e.preventDefault();
    handleChangeQuantity(name, updatedQuantity);
    setUpdatedQuantity(0);
  }

  const handleSubmit = (e : React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if(!newProduct) return;
    addProduct(newId, newProduct, newQuantity);
    setNewProduct('');
    setNewQuantity(0);
    setNewId(0);
  }

  const handleDelete = async (name : string) => {
    if(products.itemList !== undefined){
      const listItems = products.itemList.filter((item) => item.item.name !== name);
      const index = products.itemList.findIndex(item => item.item.name === name); 
      saveProducts(listItems);

      const options = {method: 'DELETE'};
      const response = await APIRequest(`${API_URL}/removeItem?id=${index}`, options);
      if(response)
        setFetchError(response);
    }
  }

  const addProduct = async (id: number, name : string, value : number) => {
    const newProductItem = {id, item: {name, eatable: false}, quantity: {value, type: 'Amount'}, averageConsumption: 0, imageSrc: '' };
    if (!products.itemList) {
      const listProducts = [products.itemList, newProductItem];
      saveProducts(listProducts);
    }
    else{
      const listProducts = [...products.itemList, newProductItem];
      saveProducts(listProducts);
    }
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(newProductItem)
    }
    const response = await APIRequest(`${API_URL}/addItem?name=${id}&quantity=${value}`, options);
    if(response)
      setFetchError(response);
  }

  const toggleTagDropdown = () => {
    setTag(!isTag);
  };      

  const toggleSortDropdown = () => {
    setSort(!isSort);
  };

  return (
    <>
      <div className="topContainerInventory">
        <img className="appImageInventory" src={image_1} alt="Imagine 1" />
        <div className="inventoryList">Inventory List</div>
      </div>
      <div className="buttonContainerInventory">
        <div className="sort">
          <button className="sortButton" onClick={toggleSortDropdown}>Sort</button>
          {isSort &&
            <div className="sortDropdown">
              <button onClick={() => {handleSort(0)}}>A-Z</button>
              <button onClick={() => {handleSort(1)}}>Z-A</button>
              <button onClick={() => {handleSort(2)}}>Quantity ↑</button>
              <button onClick={() => {handleSort(3)}}>Quantity ↓</button>
            </div>
          }
        </div>
        <button className="restock">Restock suggestions</button>
      </div>
      <ItemLists
        products={products}
        setUpdatedQuantity={setUpdatedQuantity}
        handleIncreaseQuantity={handleIncreaseQuantity}
        handleDecreaseQuantity={handleDecreaseQuantity}
        handleChangeQuantity={handleChangeQuantity}
        handleSubmit={handleSubmit}
        handleSubmitUpdate={handleSubmitUpdate}
        handleDelete={handleDelete}
        setNewId={setNewId}
        setNewProduct={setNewProduct}
        setNewQuantity={setNewQuantity}
      />
    </>
  );
}

export default Content;