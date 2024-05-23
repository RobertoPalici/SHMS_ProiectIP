import './App.css';
import React, { useState, useEffect } from 'react';
import Nav from './components/header/Nav';
import { Top } from './components/Top';
import Mid from './components/Mid';
import { ChoreProps } from './components/Chore';
import { Footer } from './components/Footer';
import Notifications from './components/Notifications'; // Import the Notifications component

const App: React.FC = () => {
  const API_URL = 'http://localhost:8081/chores';
  const API_URL_NOTIFICATIONS = 'http://localhost:8081/notifications';

  const [chores, setChores] = useState<ChoreProps>({ choresList: [] });
  const [choresHistory, setChoresHistory] = useState<ChoreProps>({ choresList: [] });
  const [newChore, setNewChore] = useState('');
  const [newDesc, setNewDesc] = useState('No details');
  const [newDuration, setNewDuration] = useState('No deadline');
  const [updatedChore, setUpdatedChore] = useState('');
  const [updatedDesc, setUpdatedDesc] = useState('No details');
  const [updatedDuration, setUpdatedDuration] = useState('No deadline');
  const [fetchError, setFetchError] = useState<any>(null);
  const [loading, setLoading] = useState(true);

  const handleUpdates = (chore: string) => {
    console.log(`Update22: ${chore}`);
    setUpdatedChore(chore);
  };

  console.log(updatedChore);

  const clearNotifications = async () => {
    try {
      const response = await fetch(`${API_URL_NOTIFICATIONS}/clearNotifications`, {
        method: 'DELETE'
      });
      if (!response.ok) throw Error('Notifications could not be cleared');
    } catch (error) {
      console.error('Error clearing notifications:', error);
    }
  };

  useEffect(() => {
    const fetchItems = async () => {
      try {
        const response1 = await fetch(API_URL);
        if (!response1.ok) throw Error('Chores were not received');
        const listChores = await response1.json();

        const response2 = await fetch(`${API_URL}/getHistory`);
        if (!response2.ok) throw Error('History was not received');
        const listChoresHistory = await response2.json();

        const notifications = await fetch(API_URL_NOTIFICATIONS);
        if (!notifications.ok) throw Error('Notifications were not received');
        const listNotifications = await notifications.json();

        setTimeout(() => {
          clearNotifications();
        }, 3000);

        setChores(prevChores => {
          return { ...prevChores, choresList: listChores.choresList };
        });
        setChoresHistory(prevChoresHistory => {
          return { ...prevChoresHistory, choresList: listChoresHistory.choresList };
        });
        setFetchError(null);
      } catch (err: unknown) {
        if (err instanceof Error) {
          setFetchError(err.message);
        } else {
          setFetchError('An unknown error occurred');
        }
      } finally {
        setLoading(false);
      }
    };

    setTimeout(() => {
      fetchItems();
    }, 2000);
  }, []);

  return (
    <div className="App">
      <Nav />
      <Top />
      <main>
        {loading && <p>Chores List is loading...</p>}
        {fetchError && <p style={{ color: 'red' }}>{`Error: ${fetchError}`}</p>}
        {!fetchError && !loading && (
          <Mid
            onData={handleUpdates}
            chores={chores}
            choresHistory={choresHistory}
            newChore={newChore}
            newDesc={newDesc}
            newDuration={newDuration}
            updatedChore={updatedChore}
            updatedDesc={updatedDesc}
            updatedDuration={updatedDuration}
            fetchError={fetchError}
            setChores={setChores}
            setChoresHistory={setChoresHistory}
            setNewChore={setNewChore}
            setNewDesc={setNewDesc}
            setNewDuration={setNewDuration}
            setUpdatedChore={setUpdatedChore}
            setUpdatedDesc={setUpdatedDesc}
            setUpdatedDuration={setUpdatedDuration}
            setFetchError={setFetchError}
          />
        )}
        <Notifications /> {/* Add the Notifications component here */}
      </main>
      <Footer />
    </div>
  );
};

export default App;
