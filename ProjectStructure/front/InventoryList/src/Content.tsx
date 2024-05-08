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

interface ContentProps{
  products: ProductProps;
  newProduct: string;
  newQuantity: number;
  setProducts: React.Dispatch<React.SetStateAction<ProductProps>>;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
}

const Content: React.FC<ContentProps> = ({products, setProducts, newProduct, setNewProduct, newQuantity, setNewQuantity}) => {
 
    const [isTagDropdownOpen, setTagDropdownOpen] = useState(false);
    const [isSortDropdownOpen, setSortDropdownOpen] = useState(false);

    const saveProducts = (newProducts : ItemList[]) => {
      setProducts(prevProducts => {
        return {...prevProducts, itemList: newProducts};
      });
      localStorage.setItem('inventoryList', JSON.stringify(newProducts));
    }

    const handleIncreaseQuantity = (name : string) =>{
      const listItems = products.itemList.map((item) => item.item.name === name && item.item.quantity.value >= 0 ? {...item, item: {...item.item, quantity: {...item.item.quantity, value: item.item.quantity.value + 1}}} : item); 
      saveProducts(listItems);
    }

    const handleDecreaseQuantity = (name : string) =>{
      const listItems = products.itemList.map((item) => item.item.name === name && item.item.quantity.value > 0? {...item, item: {...item.item, quantity: {...item.item.quantity, value: item.item.quantity.value - 1}}} : item); 
      saveProducts(listItems);
    }

    const handleSubmit = (e : React.FormEvent<HTMLFormElement>) => {
      e.preventDefault();
      if(!newProduct) return;
      addProduct(newProduct, newQuantity);
      setNewProduct('');
      setNewQuantity(0);
    }

    const handleDelete = (name : string) => {
        const listItems = products.itemList.filter((item) => item.item.name !== name);  
        saveProducts(listItems);
    }

    const addProduct = (name : string, value : number) => {
      const newProductItem = {item: {name, expiryDate: null, quantity: {value, type: 'Amount'}, averageConsumption: 0}, dateOfBuying: null, imageSrc: '' };
      if (!products.itemList) {
        const listProducts = [products.itemList, newProductItem];
        saveProducts(listProducts);
      }
      else{
      const listProducts = [...products.itemList, newProductItem];
      saveProducts(listProducts);}
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
        <main>
        <img className="appImage" src={image_1} alt="Imagine 1" />
      <img className="overlay" src={image_2} alt="Overlay" />
      <img className="vector" src={image_3} alt="Vector" />
      
      <div className="inventory-list">Inventory List</div>
     
        <div className="buttonContainer">
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
            
        </main>
    );
}

export default Content;