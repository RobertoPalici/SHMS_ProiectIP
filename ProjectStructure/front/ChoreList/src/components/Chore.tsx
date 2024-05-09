import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';

export type ChoreList = {
  name : string;
  description: string;
  personID: number;
  duration: number;
  imageSrc: string | null;
}

export type ChoreProps = {
  choreList : ChoreList[]
}

interface ChoreDeclareProps {

}

const Chore: React.FC<ChoreList & ChoreDeclareProps> = ({item, imageSrc, dateOfBuying, handleIncreaseQuantity, handleDecreaseQuantity, handleSubmit, handleDelete, newProduct, setNewProduct, newQuantity, setNewQuantity}) => {

}

export default Chore;