import React from 'react'
import Product, { ProductProps , ItemList} from './components/product/Product';
import questionmark from './components/pictures/questionmark.png';
import productIcon from './components/pictures/product.png';

interface ContentProps{
  products: ProductProps;
  
  setUpdatedQuantity: React.Dispatch<React.SetStateAction<number>>;
  handleIncreaseQuantity: (name : string) => void;
  handleDecreaseQuantity: (name : string) => void;
  handleChangeQuantity: (name : string, quantity: number) => void;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleSubmitUpdate: (e: React.FormEvent<HTMLFormElement>, name: string) => void;
  handleDelete: (name : string) => void;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
}

const ItemLists: React.FC<ContentProps> = ({products, setUpdatedQuantity, handleChangeQuantity, handleIncreaseQuantity, handleDecreaseQuantity, handleSubmit, handleSubmitUpdate, handleDelete, setNewProduct, setNewQuantity}) => {
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
                          
                          setUpdatedQuantity={setUpdatedQuantity}
                          quantity={item.quantity}
                          averageConsumption={item.averageConsumption}
                          imageSrc={productIcon}
                          handleChangeQuantity={handleChangeQuantity}
                          handleIncreaseQuantity={handleIncreaseQuantity}
                          handleDecreaseQuantity={handleDecreaseQuantity}
                          handleDelete={handleDelete}
                          handleSubmitUpdate={handleSubmitUpdate}
                          id={undefined}/>
                        </div>
                    ))}
                <div className="product-container">
                                <Product
                          item={{
                            "name": "",
                            "eatable": false
                          }}
                          quantity={{
                            "value": 0,
                            "type": "Amount"
                          }}
                          
                          averageConsumption={0}
                          imageSrc={questionmark}
                          handleChangeQuantity={handleChangeQuantity}
                          handleIncreaseQuantity={handleIncreaseQuantity}
                          handleDecreaseQuantity={handleDecreaseQuantity}
                          handleSubmit={handleSubmit}
                          handleSubmitUpdate={handleSubmitUpdate}
                          handleDelete={handleDelete}
                          setNewProduct={setNewProduct}
                          setNewQuantity={setNewQuantity}
                          setUpdatedQuantity={setUpdatedQuantity}
                          id={undefined}/>
                  </div>
                  </div>
  )
}

export default ItemLists