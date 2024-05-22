import React, { useState } from 'react';
import questionmark from '../pictures/questionmark.png';
import './Product.css';
import { useRef } from 'react';

type Quantity = {
  value: number;
  type: string;
}

export type Item = {
  name: string;
  eatable: boolean;
}

export type ItemList = {
  item: Item;
  quantity: Quantity;
  averageConsumption: number;
  imageSrc: string;
  id : any;
}

export type ProductProps = {
  itemList: ItemList[];
};

interface ProductDeclareProps {
  handleIncreaseQuantity: (name : string) => void;
  handleDecreaseQuantity: (name : string) => void;
  handleSubmit?: (e: React.FormEvent<HTMLFormElement>) => void ;
  handleDelete: (name : string) => void;
  newProduct ?: string;
  setNewProduct ?: React.Dispatch<React.SetStateAction<string>>;
  newQuantity ?: number;
  setNewQuantity?: React.Dispatch<React.SetStateAction<number>>;
}


const Product: React.FC<ItemList & ProductDeclareProps> = ({item, quantity, imageSrc, handleIncreaseQuantity, handleDecreaseQuantity, handleSubmit, handleDelete, newProduct, setNewProduct, newQuantity, setNewQuantity}) => {

  const inputRef = useRef<HTMLInputElement>(null);
  const inputRef2 = useRef<HTMLInputElement>(null);
  const[edit, setEdit] = useState(true)
  const handleEdit = () => {
    setEdit(!edit);
  }

  const handleButton = () => {
    setTimeout(() => {
      if(inputRef.current) {inputRef.current.value = '';}
      if(inputRef2.current) {inputRef2.current.value = '';}
    }, 30);
  };

  return (
    <div className="product">
      <img style={{margin: '10px'}} src={imageSrc} alt={item.name} className="product-image" />
      <div className="product-info">
        {imageSrc !== questionmark && edit && (
        <div className="quantity-controls"> 

        <button className="editButton" onClick={handleEdit}>Edit</button>
          <button className="quantity-button"
              onClick={() => handleDecreaseQuantity(item.name)}
          >-
          </button>
          <span className="current-quantity">
              {quantity.value}
          </span>
          <button className="quantity-button" 
              onClick={() => handleIncreaseQuantity(item.name)}
          >+
          </button>
          <button className="remove-button" 
              onClick={() => handleDelete(item.name)}
          >X
          </button>
        </div>
        )}
        {!edit &&
        <div className="quantity-controls"> 

        <button className="editButton" onClick={handleEdit}>Confirm</button>
          <button className="quantity-button"
              onClick={() => handleDecreaseQuantity(item.name)}
          >-
          </button>
          <form>
            <input className="inputStyle"
            type="text"
            placeholder="20"/>
          </form>
          <button className="quantity-button" 
              onClick={() => handleIncreaseQuantity(item.name)}
          >+
          </button>
          <button className="remove-button" 
              onClick={() => handleDelete(item.name)}
          >X
          </button>
          </div>
        }
        <form className="product-info" onSubmit={handleSubmit}>
        {imageSrc === questionmark ? (
          <div className='product-info'>
          <input
            autoFocus
            ref={inputRef}
            type='text'
            placeholder='Add Product' 
            required
            className="product-name"
            value={newProduct}
            onChange={(e) => {if(setNewProduct) setNewProduct(e.target.value);
            }}
          /> 
          <input
          autoFocus
          ref={inputRef2}
          type='string'
          placeholder='Add Quantity' 
          required
          className="product-name"
          value={newQuantity}
          onChange={(e) => {if(setNewQuantity) {
            const value = parseInt(e.target.value, 10);
            setNewQuantity(value);
            }
          }}
        />
        </div>
        ) : (
          <h2 className="product-name">{item.name}</h2>
        )}
        {imageSrc === questionmark && (
          <button 
              className="buy-button" 
              type='submit'
              aria-label='Add Item'
              onClick={handleButton}
          >
                Add
          </button>
        )}
        </form>
      </div>
    </div>
  );
};

export default Product;
