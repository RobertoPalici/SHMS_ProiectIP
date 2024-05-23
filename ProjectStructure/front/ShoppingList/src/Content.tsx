import React, { useEffect, useRef, useState } from 'react';
import Product, { ProductProps , ShoppingList, ShoppingItem} from './components/product/Product';
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
 
    const API_URL = 'http://localhost:8081/shopping';
    const [isTag, setTag] = useState(false);
    const [isSort, setSort] = useState(false);
    const [slist, setSlist] = useState(false);
    const [nlist, setNlist] = useState(false);
    const [listIndex, setListIndex] = useState(0);
    const [newList, setNewList] = useState('');

    const inputRef = useRef<HTMLInputElement>(null);

    const handleSlistDropdown = () =>{
      setSlist(!slist);
    };

    const handleNlistDropdown = () =>{
      setNlist(!nlist);
    };

    useEffect(() => {
        console.log('Products have been updated:', products);
    }, [products]);

    const saveProducts = (newProducts : ShoppingItem[], name: string, listIndex: number) => {
      setProducts(prevProducts => {
        const updatedProducts = { ...prevProducts };

        if (updatedProducts.shoppingLists.length >= 1) {
          updatedProducts.shoppingLists[listIndex] = {
            ...updatedProducts.shoppingLists[listIndex],
            shoppingList: newProducts, listName: name
          };
        } else {
          console.error('There are not enough shoppingLists to update.');
        }

        return updatedProducts;  
      });
    }

    const handleIncreaseQuantity = async (name : string) =>{
      if(products.shoppingLists[listIndex].shoppingList !== undefined){
        const listItems = products.shoppingLists[listIndex].shoppingList.map((item) => item.item.name === name && item.quantity.value >= 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value + 1}, price:item.price} : item); 
        saveProducts(listItems, products.shoppingLists[listIndex].listName, listIndex);

        const targetProduct = listItems.filter((item) => item.item.name === name);
        const index = products.shoppingLists[listIndex].shoppingList.findIndex(item => item.item.name === name);
        const options = {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            item: {
              name: targetProduct[0].item.name
            },
            quantity: {
              value: targetProduct[0].quantity.value,
              type: targetProduct[0].quantity.type 
            },
            price: targetProduct[0].price
          })
        }
        const response = await APIRequest(`${API_URL}/changeQuantity?index=0&id=${index}&quantity=${targetProduct[0].quantity.value}`, options);
        if(response)
          setFetchError(response);
      }
    }

    const handleDecreaseQuantity = async (name : string) =>{
      if(!products.shoppingLists[listIndex].shoppingList){
        const listItems = products.shoppingLists[listIndex].shoppingList.map((item) => item.item.name === name && item.quantity.value > 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value - 1}, price:item.price} : item); 
        saveProducts(listItems, products.shoppingLists[listIndex].listName, listIndex);

        const targetProduct = listItems.filter((item) => item.item.name === name);
        const index = products.shoppingLists[listIndex].shoppingList.findIndex(item => item.item.name === name);
        const options = {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            item: {
              name: targetProduct[0].item.name
            },
            quantity: {
              value: targetProduct[0].quantity.value,
              type: targetProduct[0].quantity.type 
            },
            price: targetProduct[0].price
          })
          
        }
        const response = await APIRequest(`${API_URL}/changeQuantity?index=0&id=${index}&quantity=${targetProduct[0].quantity.value}`, options);    
        if(response)
          setFetchError(response);
      }
    }

    const handleSubmit = (e : React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      if(!newProduct) return;
      addProduct(newProduct, newQuantity);
      setNewProduct('');
      setNewQuantity(0);
    }

    const handleDelete = async (name : string) => {
      if(products.shoppingLists !== undefined){
        const listItems = products.shoppingLists[listIndex].shoppingList.filter((item) => item.item.name !== name);
        const targetProduct = products.shoppingLists[listIndex].shoppingList.filter((item) => item.item.name === name);
        const index = products.shoppingLists[listIndex].shoppingList.findIndex(item => item.item.name === name); 
        saveProducts(listItems, products.shoppingLists[listIndex].listName, listIndex);

        const options = {method: 'DELETE'};
        const response = await APIRequest(`${API_URL}/removeItem?index=${listIndex}&id=${index}`, options);
        if(response)
          setFetchError(response);
      }
    }

    const handleListSelect = async (index: number) => {
      if(products.shoppingLists !== undefined){
        setListIndex(index);
      }
    }

    const addProduct = async (name : string, value : number) => {
      const newProductItem = {id: undefined, item: {name}, quantity: {value, type: 'Amount'}, price: 0, imageSrc: '' };
      const listCopy = products.shoppingLists[listIndex]
      if (!listCopy.shoppingList) {
        const listProducts = [listCopy.shoppingList, newProductItem];
        saveProducts(listProducts, products.shoppingLists[listIndex].listName, listIndex);
      }
      else{
        const listProducts = [...products.shoppingLists[listIndex].shoppingList, newProductItem];
        saveProducts(listProducts, products.shoppingLists[listIndex].listName, listIndex);
      }
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newProductItem)
      }
      const response = await APIRequest(`${API_URL}/addItem?index=${listIndex}&name=${name}&quantity=${value}`, options);
      if(response)
        setFetchError(response);
    }

    const addList = async (name : string) => {
      const newList: ShoppingList = {shoppingList: [], listName: name};
      if (products.shoppingLists) {
        products.shoppingLists.push(newList);
        setProducts(products);
      }
      else{
        products.shoppingLists = [newList];
        setProducts(products);
      }
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newList)
      }
      const response = await APIRequest(`${API_URL}/addList?name=${name}`, options);
      if(response)
        setFetchError(response);
    }

    const handleSubmitList = (e : React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      if(!newList) return;
      addList(newList);
      setNewList('');
    }
  
    const toggleTagDropdown = () => {
      setTag(!isTag);
    };      
  
    const toggleSortDropdown = () => {
      setSort(!isSort);

    };

    const handleButton = () => {
      setTimeout(() => {
        if(inputRef.current) {inputRef.current.value = '';}
      }, 30);
    };

    return (
        <>
        <div className="topContainer">
          <img className="appImage" src={image_1} alt="Imagine 1" />
          <img className="vector" src={image_3} alt="Vector" />
        
          <div className="shopping-list">Shopping Lists</div>
          </div>
          <div className="itemListButtons">
            <div className="newList">
              <button className="bigButtons" onClick={handleNlistDropdown}>Add new shopping list</button>
              {nlist &&
              <div className="newListPrompt">
                <img src={productIcon}></img>
                <form className="formStyle" onSubmit={(e) => handleSubmitList(e)}>
                  <input
                    autoFocus
                    type="text"
                    required
                    placeholder="Name"
                    value={newList}
                    ref={inputRef}
                    onChange={(e) => {if(setNewList) setNewList(e.target.value)}}
                  />
                  <button className="addNewList" type='submit' onClick={handleButton}>Add list</button>
                </form>
                
              </div>
              }
            </div>
            <div className="existingList">
            <button className="bigButtons" onClick={handleSlistDropdown}>Select existing shopping list</button>
            {slist &&
            <div className="existingListDropdown">
              {products.shoppingLists?.map((object, index) => {
                return (
                 <button onClick={() => handleListSelect(index)} key={index}>{object.listName}</button>
              )})}
            </div>
            }
            </div>
          </div>
          <ItemLists
            products={products}
            listIndex={listIndex}
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