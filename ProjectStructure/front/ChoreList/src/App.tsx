import './App.css'
import React, { useState, useEffect } from 'react';
import  Nav  from './components/header/Nav'
import {Top} from './components/Top'
import Mid from './components/Mid'
import { ChoreProps } from './components/Chore';
import {Footer} from './components/Footer';


const App: React.FC = () => {

  const API_URL = 'http://localhost:8081/chores';

  const [chores, setChores] = useState<ChoreProps>({choresList: []});
  const [newChore, setNewChore] = useState('');
  const [newDesc, setNewDesc] = useState('');
  const [newDuration, setNewDuration] = useState('');
  const [updatedChore, setUpdatedChore] = useState('');
  const [updatedDesc, setUpdatedDesc] = useState('');
  const [updatedDuration, setUpdatedDuration] = useState('');
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  const handleUpdates = (chore: string) => {
    console.log(`Update22: ${chore}`);
    setUpdatedChore(chore);
  }

  console.log(updatedChore);

  useEffect(() => {
    const fetchItems = async () => {
      try{
        const response = await fetch(API_URL);
        if(!response.ok) throw Error('Data was not received');
        const listChores = await response.json();
        console.log(listChores);
        console.log(listChores.choresList);
        setChores(prevChores => {
          return {...prevChores, choresList: listChores.choresList};
        });
        setFetchError(null);
      } catch(err : unknown){
          if (err instanceof Error) {
            setFetchError(err.message);
          } else {
            setFetchError('An unknown error occurred');
          }
      } finally{
        setLoading(false);
      }
    }
    setTimeout(() => {
      (async () => await fetchItems())();
    }, 2000);
  }, [])

  return (
    <div className="App">
      <Nav />
      <Top />
      <main>
        {loading && <p>Chores List is loading...</p>}
        {fetchError && <p style={{color: "red"}}>{`Error: ${fetchError}`}</p>}
        {!fetchError && !loading && <Mid
          onData={handleUpdates} 
          chores = {chores}
          newChore = {newChore}
          newDesc = {newDesc}
          newDuration={newDuration}
          updatedChore = {updatedChore}
          updatedDesc = {updatedDesc}
          updatedDuration={updatedDuration}
          fetchError = {fetchError}
          setChores = {setChores}
          setNewChore = {setNewChore}
          setNewDesc = {setNewDesc}
          setNewDuration={setNewDuration}
          setUpdatedChore={setUpdatedChore}
          setUpdatedDesc={setUpdatedDesc}
          setUpdatedDuration={setUpdatedDuration}
          setFetchError = {setFetchError}
        />}
      </main>
      <Footer />  
    </div>
  );
}

export default App;
