import React, { useState } from 'react';
import questionmark from '../pictures/questionmark.png';
import './Product.css';
import { useRef } from 'react';

type Quantity = {
  value: number;
  type: string;
}

type Item = {
  name: string;
  expiryDate: string | null;
  quantity: Quantity;
  averageConsumption: number;
}

export type ItemList = {
  item: Item;
  imageSrc : string;
  dateOfBuying: string | null;
}

export type ProductProps = {
  itemList : ItemList[];
};

interface ProductDeclareProps {
  handleIncreaseQuantity: (name : string) => void;
  handleDecreaseQuantity: (name : string) => void;
  handleSubmit?: (e: React.FormEvent<HTMLFormElement>) => void ;
  handleDelete?: (name : string) => void;
  newProduct ?: string;
  setNewProduct ?: React.Dispatch<React.SetStateAction<string>>;
  newQuantity ?: number;
  setNewQuantity?: React.Dispatch<React.SetStateAction<number>>;
}


const Product: React.FC<ItemList & ProductDeclareProps> = ({item, imageSrc, dateOfBuying, handleIncreaseQuantity, handleDecreaseQuantity, handleSubmit, handleDelete, newProduct, setNewProduct, newQuantity, setNewQuantity}) => {
  const [quantityValue, setQuantityValue] = useState(item.quantity.value);
  const [productName, setProductName] = useState(item.name);

  const inputRef = useRef<HTMLInputElement>(null);
  const inputRef2 = useRef<HTMLInputElement>(null);

  return (
    <div className="product">
      <img style={{margin: '10px'}} src={imageSrc} alt={productName} className="product-image" />
      <div className="product-info">
        {imageSrc !== questionmark && (
        <div className="quantity-controls">
          <button className="quantity-button"
              onClick={() => handleDecreaseQuantity(item.name)}
          >-
          </button>
          <span className="current-quantity">
              {item.quantity.value}
          </span>
          <button className="quantity-button" 
              onClick={() => handleIncreaseQuantity(item.name)}
          >+
          </button>
          <button className="remove-button" 
              onClick={() => {if(handleDelete) handleDelete(item.name)}}
          >X
          </button>
        </div>
        )}
        <form className="product-info" onSubmit={handleSubmit}>
        {imageSrc === questionmark ? (
          <div>
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
          <h2 className="product-name">{productName}</h2>
        )}
        {imageSrc === questionmark && (
          <button 
              className="buy-button" 
              type='submit'
              aria-label='Add Item'
              onClick={() => {
                inputRef.current?.focus()
                inputRef2.current?.focus()
                }}
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
