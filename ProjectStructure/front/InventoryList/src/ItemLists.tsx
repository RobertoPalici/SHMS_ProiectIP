import React from 'react'
import Product, { ProductProps , ItemList} from './components/product/Product';
import questionmark from './components/pictures/questionmark.png';
import productIcon from './components/pictures/product.png';

interface ContentProps{
  products: ProductProps;
  handleIncreaseQuantity: (name : string) => void;
  handleDecreaseQuantity: (name : string) => void;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleDelete: (name : string) => void;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
}

const ItemLists: React.FC<ContentProps> = ({products, handleIncreaseQuantity, handleDecreaseQuantity, handleSubmit, handleDelete, setNewProduct, setNewQuantity}) => {
  const {itemList} = products;

  return (
    <div className="product-list">
                    {itemList?.map((item, index) => (
                        <div className="product-container" key={index}>
                                <Product
                          item={item.item}
                          imageSrc={productIcon}
                          dateOfBuying={item.dateOfBuying}
                          handleIncreaseQuantity={handleIncreaseQuantity}
                          handleDecreaseQuantity={handleDecreaseQuantity}
                          handleDelete={handleDelete}                               />
                        </div>
                    ))}
                <div className="product-container">
                                <Product
                                item={{
                                    "name": "",
                                    "expiryDate": null,
                                    "quantity": {
                                      "value": 0,
                                      "type": "Amount"
                                    },
                                    "averageConsumption": 0
                                  }} 
                                imageSrc={questionmark}
                                dateOfBuying={null}
                                handleIncreaseQuantity={handleIncreaseQuantity}
                                handleDecreaseQuantity={handleDecreaseQuantity}
                                handleSubmit={handleSubmit} 
                                setNewProduct={setNewProduct}
                                setNewQuantity={setNewQuantity}
                                />
                  </div>
                  </div>
  )
}

export default ItemLists