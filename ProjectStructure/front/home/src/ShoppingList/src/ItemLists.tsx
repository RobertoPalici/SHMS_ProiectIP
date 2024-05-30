import React from 'react'
import Product, { ProductProps , ShoppingList, ShoppingItem} from './components/product/Product';
import './components/product/Product.css';
import questionmark from './components/pictures/questionmark.png';
import productIcon from './components/pictures/product.png';

interface ContentProps{
  products: ProductProps;
  listIndex: number;
  buyItem: (name: string) => void;
  handleIncreaseQuantity: (name : string) => void;
  handleDecreaseQuantity: (name : string) => void;
  handleSubmit: (e : React.FormEvent<HTMLFormElement>) => void;
  handleDelete: (name : string) => void;
  setNewId: React.Dispatch<React.SetStateAction<number>>;
  setNewProduct: React.Dispatch<React.SetStateAction<string>>;
  setNewQuantity: React.Dispatch<React.SetStateAction<number>>;
}

const ItemLists: React.FC<ContentProps> = ({
  products, 
  listIndex, 
  buyItem, 
  handleIncreaseQuantity, 
  handleDecreaseQuantity, 
  handleSubmit, 
  handleDelete,
  setNewId, 
  setNewProduct, 
  setNewQuantity
}) => {

  return (
    <div className="productListShopping">
      {products.shoppingLists[listIndex].shoppingList?.map((item, index) => (
        <div className="productContainerShopping" key={index}>
          <Product
            item={item.item}
            imageSrc={productIcon}
            price={item.price}
            quantity={item.quantity}
            buyItem={buyItem}
            handleIncreaseQuantity={handleIncreaseQuantity}
            handleDecreaseQuantity={handleDecreaseQuantity}
            handleDelete={handleDelete}
            id={undefined}
          />
        </div>
      ))}
      <div className="productContainerShopping">
        <Product
          item={{
            "name": ""
          }}
          quantity={{
            "value": 0,
            "type": "Amount"
          }}
          imageSrc={questionmark}
          price={0}
          handleIncreaseQuantity={handleIncreaseQuantity}
          handleDecreaseQuantity={handleDecreaseQuantity}
          handleSubmit={handleSubmit}
          handleDelete={handleDelete}
          setNewId={setNewId}
          setNewProduct={setNewProduct}
          setNewQuantity={setNewQuantity}
          id={undefined}
        />
      </div>
    </div>
  )
}

export default ItemLists