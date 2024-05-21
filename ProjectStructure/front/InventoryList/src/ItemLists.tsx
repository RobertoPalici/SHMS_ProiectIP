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
  console.log(products);
  console.log(products.itemList);
  console.log(itemList);

  return (
    <div className="product-list">
                    {itemList?.map((item, index) => (
                        <div className="product-container" key={index}>
                                <Product
                          item={item.item}
                          quantity={item.quantity}
                          imageSrc={productIcon}
                          handleIncreaseQuantity={handleIncreaseQuantity}
                          handleDecreaseQuantity={handleDecreaseQuantity}
                          handleDelete={handleDelete}
                          id={undefined}/>
                        </div>
                    ))}
                <div className="product-container">
                                <Product
                          item={{
                            "name": "",
                            "expiryDate": null,
                            "averageConsumption": 0,
                            "eatable": false
                          }}
                          quantity={{
                            "value": 0,
                            "type": "Amount"
                          }}
                          imageSrc={questionmark}
                          handleIncreaseQuantity={handleIncreaseQuantity}
                          handleDecreaseQuantity={handleDecreaseQuantity}
                          handleSubmit={handleSubmit}
                          handleDelete={handleDelete}
                          setNewProduct={setNewProduct}
                          setNewQuantity={setNewQuantity}
                          id={undefined}/>
                  </div>
                  </div>
  )
}

export default ItemLists