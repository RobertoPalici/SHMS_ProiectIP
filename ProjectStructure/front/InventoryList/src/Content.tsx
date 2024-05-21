import React, { useState } from 'react';
import Product, { ProductProps , ItemList} from './components/product/Product';
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
import productIcon from './components/pictures/product.png';
import ItemLists from './ItemLists';
import APIRequest from './APIRequest';

interface ContentProps{
  products: ProductProps;
  newProduct: string;
  newQuantity: number;
  fetchError: any;
  setProducts: React.Dispatch<React.SetStateAction<ProductProps>>;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
  setFetchError: React.Dispatch<any>;
}

const Content: React.FC<ContentProps> = ({products, setProducts, newProduct, setNewProduct, newQuantity, setNewQuantity, fetchError, setFetchError}) => {
 
    const [isTagDropdownOpen, setTagDropdownOpen] = useState(false);
    const [isSortDropdownOpen, setSortDropdownOpen] = useState(false);

    const API_URL = 'http://localhost:8081/inventory';

    const saveProducts = (newProducts : ItemList[]) => {
      console.log(products);
      setProducts(prevProducts => {
        return {...prevProducts, itemList: newProducts};  
      });
    }

    const handleIncreaseQuantity = async (name : string) =>{
      if(products.itemList!==undefined){
        const listItems = products.itemList.map((item) => item.item.name === name && item.quantity.value > 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value + 1}, averageConsumption: item.averageConsumption} : item); 
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
        //const response = await APIRequest(`${API_URL}/${targetProduct[0].id}`, options);     
        if(response)
          setFetchError(response);
      }
    }

    const handleSubmit = (e : React.FormEvent<HTMLFormElement>) => {
      console.log(products);
      e.preventDefault();
      if(!newProduct) return;
      addProduct(newProduct, newQuantity);
      setNewProduct('');
      setNewQuantity(0);
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

    const addProduct = async (name : string, value : number) => {
      console.log(products);
      console.log(products.itemList);
      const newProductItem = {id: undefined, item: {name, eatable: false}, quantity: {value, type: 'Amount'}, averageConsumption: 0, imageSrc: '' };
      if (!products.itemList) {
        const listProducts = [products.itemList, newProductItem];
        saveProducts(listProducts);
      }
      else{
      const listProducts = [...products.itemList, newProductItem];
      saveProducts(listProducts);}
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newProductItem)
      }
      const response = await APIRequest(`${API_URL}/addItem?name=${name}&quantity=${value}`, options);
      if(response)
        setFetchError(response);
    }
  
    const toggleTagDropdown = () => {
      setTagDropdownOpen(!isTagDropdownOpen);
      if (isSortDropdownOpen) {
        setSortDropdownOpen(false);
      }
    };      
  
    const toggleSortDropdown = () => {
      setSortDropdownOpen(!isSortDropdownOpen);
      if (isTagDropdownOpen) {
        setTagDropdownOpen(false);
      }
    };

    return (
        <>
          <img className="appImage" src={image_1} alt="Imagine 1" />
          <img className="overlay" src={image_2} alt="Overlay" />
          <img className="vector" src={image_3} alt="Vector" />
        
          <div className="inventory-list">Inventory List</div>
     
          {/* <div className="buttonContainer">
              <button onClick={toggleTagDropdown} className="tagButton">Tag</button>
              {isTagDropdownOpen && (
              <div className="dropdowntag">
                  <button> Food</button>
                  <button>Others</button>
              </div>
              )}
              <button onClick={toggleSortDropdown} className="sortButton">Sort</button>
              {isSortDropdownOpen && (
              <div className="dropdownsort">
                  <button>Quantity</button>
                  <button>Date of add</button>
                  <button>A..Z</button>
                  <button>Z..A</button>
              </div>
              )}
              <div className="nav-links"></div>
            <button className="restockSuggestions">Restock Suggestions</button>
          </div> */}
          <ItemLists
            products={products}
            handleIncreaseQuantity={handleIncreaseQuantity}
            handleDecreaseQuantity={handleDecreaseQuantity}
            handleSubmit={handleSubmit}
            handleDelete={handleDelete}
            setNewProduct={setNewProduct}
            setNewQuantity={setNewQuantity}
          />
        </>
    );
}

export default Content;