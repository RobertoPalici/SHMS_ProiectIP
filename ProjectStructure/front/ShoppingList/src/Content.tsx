import React, { useEffect, useState } from 'react';
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
 
    const [isTag, setTag] = useState(false);
    const [isSort, setSort] = useState(false);
    const [slist, setSlist] = useState(false);
    const [nlist, setNlist] = useState(false);
    const handleSlistDropdown = () =>{
      setSlist(!slist);
    };
    const handleNlistDropdown = () =>{
      setNlist(!nlist);
    };


    const API_URL = 'http://localhost:8081/shopping';

    useEffect(() => {
        console.log('Products have been updated:', products);
    }, [products]);

    const saveProducts = (newProducts : ShoppingItem[]) => {
      setProducts(prevProducts => {
        const updatedProducts = { ...prevProducts };

        if (updatedProducts.shoppingLists.length >= 2) {
          updatedProducts.shoppingLists[1] = {
            ...updatedProducts.shoppingLists[1],
            shoppingList: newProducts,
          };
        } else {
          console.error('There are not enough shoppingLists to update.');
        }

        return updatedProducts;  
      });
    }

    const handleIncreaseQuantity = async (name : string) =>{
      if(products.shoppingLists[1].shoppingList !== undefined){
        const listItems = products.shoppingLists[1].shoppingList.map((item) => item.item.name === name && item.quantity.value >= 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value + 1}, price:item.price} : item); 
        saveProducts(listItems);

        const targetProduct = listItems.filter((item) => item.item.name === name);
        const index = products.shoppingLists[1].shoppingList.findIndex(item => item.item.name === name);
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
      if(products.shoppingLists !== undefined){
        const listItems = products.shoppingLists[1].shoppingList.map((item) => item.item.name === name && item.quantity.value > 0? {...item, item: {...item.item}, quantity: {...item.quantity, value: item.quantity.value - 1}, price:item.price} : item); 
        saveProducts(listItems);

        const targetProduct = listItems.filter((item) => item.item.name === name);
        const index = products.shoppingLists[1].shoppingList.findIndex(item => item.item.name === name);
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
      console.log(products);
      e.preventDefault();
      if(!newProduct) return;
      addProduct(newProduct, newQuantity);
      console.log(products);
      setNewProduct('');
      setNewQuantity(0);
    }

    const handleDelete = async (name : string) => {
      if(products.shoppingLists !== undefined){
        const listItems = products.shoppingLists[1].shoppingList.filter((item) => item.item.name !== name);
        const targetProduct = products.shoppingLists[1].shoppingList.filter((item) => item.item.name === name);
        const index = products.shoppingLists[1].shoppingList.findIndex(item => item.item.name === name); 
        saveProducts(listItems);

        const options = {method: 'DELETE'};
        const response = await APIRequest(`${API_URL}/removeItem?index=0&id=${index}`, options);
        if(response)
          setFetchError(response);
      }
    }

    const addProduct = async (name : string, value : number) => {
      console.log(products);
      console.log(products.shoppingLists[1].shoppingList);
      const newProductItem = {id: undefined, item: {name}, quantity: {value, type: 'Amount'}, price: 0, imageSrc: '' };
      if (!products.shoppingLists[1].shoppingList) {
        console.log(products.shoppingLists[1].shoppingList);
        const listProducts = [products.shoppingLists[1].shoppingList, newProductItem];
        console.log(listProducts);
        saveProducts(listProducts);
      }
      else{
      const listProducts = [...products.shoppingLists[1].shoppingList, newProductItem];
      console.log(listProducts);
      saveProducts(listProducts);}
      const options = {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(newProductItem)
      }
      const response = await APIRequest(`${API_URL}/addItem?index=0&name=${name}&quantity=${value}`, options);
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
                <form>
                  <input>
                  </input>
                </form>
                <button className="addNewList">Add list</button>
              </div>
              }
            </div>
            <div className="existingList">
            <button className="bigButtons" onClick={handleSlistDropdown}>Select existing shopping list</button>
            {slist &&
            <div className="existingListDropdown">
              <button>1</button>
              <button>2</button>
              <button>3</button>
            </div>
            }
            </div>
          </div>
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