import React, { useState, useEffect, useRef } from 'react';
import styles from './Mid.module.css'
import placeholder from './pictures/sweep.jpg'
import APIRequest from '../APIRequest';

export type ChoresList = {
  name : string | null;
  description: string;
  personID: number;
  duration: string;
  imageSrc: string | null;
}

export type ChoreProps = {
  choresList : ChoresList[];
}

interface ChoreDeclareProps {
	handleDelete: (name : string) => void;
	handleDone: (name : string) => void;
	handleSubmit?: (e: React.FormEvent<HTMLFormElement>) => void ;
	handleSubmitUpdate?: (e: React.FormEvent<HTMLFormElement>, oldName: string) => void ;
	newChore?: string;
	setNewChore?: React.Dispatch<React.SetStateAction<string>>;
	newDesc?: string;
	setNewDesc?: React.Dispatch<React.SetStateAction<string>>;
	newDuration?: string;
	setNewDuration?: React.Dispatch<React.SetStateAction<string>>;
	updatedChore?: string;
	setUpdatedChore: React.Dispatch<React.SetStateAction<string>>;
	updatedDesc?: string;
	setUpdatedDesc: React.Dispatch<React.SetStateAction<string>>;
	updatedDuration?: string;
	setUpdatedDuration: React.Dispatch<React.SetStateAction<string>>;
}

const Chore: React.FC<ChoresList & ChoreDeclareProps> = ({
	name, 
	description, 
	duration, 
	personID, 
	imageSrc, 
	handleDone, 
	handleSubmit, 
	handleSubmitUpdate, 
	handleDelete,
	newChore, 
	setNewChore, 
	newDesc, 
	setNewDesc, 
	newDuration, 
	setNewDuration, 
	updatedChore, 
	setUpdatedChore, 
	updatedDesc, 
	setUpdatedDesc, 
	updatedDuration, 
	setUpdatedDuration
}) => {

	const inputRef = useRef<HTMLInputElement>(null);
	const inputRef2 = useRef<HTMLInputElement>(null);
	const inputRef3 = useRef<HTMLInputElement>(null);
	const [edit, setEdit] = useState(true);
	const [confirm, setConfirm] = useState(false);

	const handleConfirm = () => {
		setConfirm(!confirm);
	}

	const handleButton = () => {
		setTimeout(() => {
			if(inputRef.current) {inputRef.current.value = '';}
			if(inputRef2.current) {inputRef2.current.value = '';}
			if(inputRef3.current) {inputRef3.current.value = '';}
		}, 30);
	};

	const handleEdit = () => {
			setEdit(!edit);
			if(name !== null && setNewChore){
					setNewChore(name);
			}
	}

	const handleSubmitEdit = (e : React.FormEvent<HTMLFormElement>, oldName: string) => {
			e.preventDefault();
			if(handleSubmitUpdate){
					handleSubmitUpdate(e, oldName);}
			handleEdit();
	}

	return (
		<>
			{imageSrc === null && (
				<form className={styles.formStyle} onSubmit={handleSubmit}>
					<input
						autoFocus
						ref={inputRef}
						type="text"
						placeholder="Title"
						required
						value={newChore}
						onChange={(e) => {if(setNewChore) setNewChore(e.target.value)}}
					/>
					<input
						autoFocus
						ref={inputRef2}
						type="text"
						placeholder="Details"
						value={newDesc}
						onChange={(e) => {if(setNewDesc) setNewDesc(e.target.value)}}
					/>
					<input
						autoFocus
						ref={inputRef3}
						type="text"
						placeholder="Deadline"
						value={newDuration}
						onChange={(e) => {if(setNewDuration) setNewDuration(e.target.value)}}
					/>
					<button type="submit" onClick={handleButton}>Add chore</button>
				</form>
			)}
			{imageSrc!== null && (
				<>
					<img src={placeholder} alt="" />
					{edit && !confirm &&
						<div className={styles.bottom}>
							<div className={styles.textStyle}>
								<h1>{name}</h1>
								<div className={styles.details}>
										<h2>Details: </h2>
										<p>{description}</p>
								</div>
								<div className ={styles.details}>
										<h2>Deadline: </h2>
										<p>{duration}</p>
								</div>
							</div>
							<div className={styles.buttonContainer}>
								<button className={styles.buttonStyle} onClick={handleConfirm}>Remove</button>
								<button className={styles.button2Style} onClick={handleEdit}>Edit</button>
								<button className={styles.button3Style} onClick={() => {if(name) handleDone(name)}}>Done</button>
							</div>
						</div>
					}
					{edit && confirm &&
						<div className={styles.bottomConfirm}>
							<div className={styles.textStyleConfirm}>
								<h1>Are you sure you want to delete this chore?</h1>
							</div>
							<div className={styles.buttonContainerConfirm}>
								<button className={styles.button3Style} onClick={() => {if(name) handleDelete(name); handleConfirm();}}>Yes</button>
								<button className={styles.buttonStyle} onClick={handleConfirm}>No</button>
							</div>
						</div>
					}
					{!edit && 
						<>
							<form className={styles.editStyles} onSubmit={(e) => {if(name) handleSubmitEdit(e,name)}}>
								<div className={styles.bottom}>
									<div className={styles.textStyle}>
										<input
											type="text"
											placeholder="Name"
											value={updatedChore}
											ref={inputRef}
											onChange={(e) => {if(setUpdatedChore) setUpdatedChore(e.target.value)}}
										/>
										<div className={styles.details}>
											<h2>Details: </h2>
											<input
												type="text"
												placeholder="Details"
												value={updatedDesc}
												ref={inputRef2}
												onChange={(e) => {if(setUpdatedDesc) setUpdatedDesc(e.target.value)}}
											/>
										</div>
										<div className ={styles.duration}>
											<h2>Deadline: </h2>
											<input
												type="text"
												placeholder="Deadline"
												value={updatedDuration}
												ref={inputRef3}
												onChange={(e) => {if(setUpdatedDuration) setUpdatedDuration(e.target.value)}}
											/>
										</div>
									</div>
									<div className={styles.buttonContainer}>
										<button className={styles.buttonStyle} onClick={() => {if(name) handleDelete(name)}}>Remove</button>
										<button className={styles.button2Style} onClick={handleEdit}>Return</button>
										<button className={styles.button4Style} type="submit">Confirm</button>
									</div>
								</div>
							</form>
						</>
					}
				</>
			)}
		</>
  )
}

export default Chore;