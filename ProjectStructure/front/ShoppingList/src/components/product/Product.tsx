import React, { useState } from 'react';
import './Product.css';

type ProductProps = {
  name: string;
  imageSrc: string;
  initialQuantity: number;
};

const Product: React.FC<ProductProps> = ({ name, imageSrc, initialQuantity }) => {
  const [quantity, setQuantity] = useState(initialQuantity);

  const decreaseQuantity = () => {
    if (quantity > 0) {
      setQuantity(quantity - 1);
    }
  };

  const increaseQuantity = () => {
    setQuantity(quantity + 1);
  };

  return (
    <div className="product">
      <img src={imageSrc} alt={name} className="product-image" />
      <div className="product-info">
        <h2 className="product-name">{name}</h2>
        <div className="quantity-controls">
          <button className="quantity-button" onClick={decreaseQuantity}>-</button>
          <span className="current-quantity">{quantity}</span>
          <button className="quantity-button" onClick={increaseQuantity}>+</button>
        </div>
      </div>
    </div>
  );
};

export default Product;
