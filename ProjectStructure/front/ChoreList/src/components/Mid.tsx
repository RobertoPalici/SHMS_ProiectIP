import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';
import ChoreLists from './ChoreLists';
import { ChoresList, ChoreProps } from './Chore';



interface MidProps {
  chores: ChoreProps;
  newChore: string;
  newDesc: string;
  newDuration: number;
  fetchError: any;
  setChores: React.Dispatch<React.SetStateAction<ChoreProps>>;
  setNewChore: React.Dispatch<React.SetStateAction<string>>;
  setNewDesc: React.Dispatch<React.SetStateAction<string>>;
  setNewDuration: React.Dispatch<React.SetStateAction<number>>;
  setFetchError: React.Dispatch<any>; 
}

const Mid: React.FC<MidProps> = ({chores, newChore, newDesc, newDuration, fetchError, setChores, setNewChore, setNewDesc, setNewDuration, setFetchError}) =>{
    const API_URL = 'http://localhost:8081/chores';

    const [updatedChore, setUpdatedChore] = useState('');
    const [updatedDesc, setUpdatedDesc] = useState('');
    const [updatedDuration, setUpdatedDuration] = useState(0);

    const saveChores = (newChores : ChoresList[]) => {
      console.log(chores);
      setChores(prevChores => {
        return {...prevChores, choresList: newChores};  
      });
    }
    
    const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
      console.log(chores);
      e.preventDefault();
      if(!newChore) return;
      addChore(newChore, newDesc, newDuration);
      setNewChore('');
      setNewDesc("No details");
    }

    const handleSubmitUpdate = (e : React.FormEvent<HTMLFormElement>, oldChore: string) => {
      e.preventDefault();
      handleUpdate(oldChore ,updatedChore, updatedDesc, updatedDuration);
      setUpdatedChore('');
      setUpdatedDesc('');
      setUpdatedDuration(0);
    }

    const handleUpdate = async (oldName: string, updatedName: string, description: string, duration: number) => {
      if(chores.choresList !== undefined){
        const listChores = chores.choresList.map((chore) => chore.name === oldName ? {...chore, name: updatedName, description, personID: -1, duration} : chore); 
        console.log('1');
        saveChores(listChores);
        console.log('4');

        const targetChore = listChores.filter((chore) => chore.name === updatedName);
        console.log('5');
        console.log(listChores[0].name);
        console.log(targetChore[0].name);
        const index = chores.choresList.findIndex(chore => chore.name === oldName);
        console.log(index);
        const options = {
          method: 'PATCH',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({
            name: targetChore[0].name,
            description: targetChore[0].description,
            personID: -1,
            duration: targetChore[0].duration
          })
        }
        console.log('2');
        const response = await APIRequest(`${API_URL}/changeItemDetails?id=${index}&name=${targetChore[0].name}&description=${targetChore[0].description}&personID=${targetChore[0].personID}&duration=${targetChore[0].duration}`, options);
        //const response = await APIRequest(`${API_URL}/${targetProduct[0].id}`, options);     
        if(response)
          setFetchError(response);
      }
    }

    const addChore = async (name: string, description: string, duration: number) => {
      if(name.trim() === ''){
        alert('Please enter a title for the chore');
      } else {
        console.log(name);
        console.log(chores);
        console.log(chores.choresList);
        const newChoreItem = {id: undefined, name, description, personID: -1, duration, imageSrc: '' };
        console.log(newChoreItem);
        if (!chores) {
          const listChores = [chores, newChoreItem];
          console.log(listChores);
          saveChores(listChores);
        }
        else{
        const listChores = [...chores.choresList, newChoreItem];
        console.log(listChores);
        saveChores(listChores);}
        const options = {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(newChoreItem)
        }
        const response = await APIRequest(`${API_URL}/addChore?name=${name}&description=${description}&duration=${duration}`, options);
        if(response)
          setFetchError(response);
      }
    }
    const handleDelete = async (name : string) => {
      if(chores.choresList !== undefined){
        const listChores = chores.choresList.filter((item) => item.name !== name);
        const targetChore = chores.choresList.filter((item) => item.name === name);
        const index = chores.choresList.findIndex(item => item.name === name); 
        saveChores(listChores);

        const options = {method: 'DELETE'};
        const response = await APIRequest(`${API_URL}/removeChore?id=${index}`, options);
        if(response)
          setFetchError(response);
      }
    }

    return(
      <ChoreLists
        chores={chores}
        handleDelete={handleDelete}
        handleSubmit={handleSubmit}
        handleSubmitUpdate={handleSubmitUpdate}
        handleUpdate={handleUpdate}
        setNewChore={setNewChore}
        setNewDesc={setNewDesc}
        setNewDuration={setNewDuration}
        setUpdatedChore={setUpdatedChore}
        setUpdatedDesc={setUpdatedDesc}
        setUpdatedDuration={setUpdatedDuration}
    />
  );
};

export default Mid;
