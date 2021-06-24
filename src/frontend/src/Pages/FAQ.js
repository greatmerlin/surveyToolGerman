import React from "react";
import { Button, Card } from "react-bootstrap";
import Title from "../Components/Title";

/**
 * Non-interactive page for frequently asked questions.
 *
 * @returns JSX.Element
 */

export default function FAQ() {
	const faqList = [
		{
			question: "Kann eine Umfrage nach der Erstellung editiert werden?",
			answer:
				"Nein, diese Funktionalität steht zum jetzigen Zeitpunkt nicht zur Verfügung.",
		},
		{
			question: "Kann ich meine Teilnahme an einer Umfrage editieren?",
			answer:
				"Nein, diese Funktionalität steht zum jetzigen Zeitpunkt nicht zur Verfügung.",
		},
		{
			question:
				"Kann ich als Ersteller sehen, welche Stimme zu welchem Teilnehmenden gehört?",
			answer:
				"Nein, diese Funktionalität steht zum jetzigen Zeitpunkt nicht zur Verfügung.",
		},
	];

	return (
		<>
			<Title>FAQ</Title>
			<ul style={{ listStyle: "none" }} className="p-0">
				{faqList.map(({ question, answer }, i) => {
					return (
						<li className="my-3" key={i}>
							<Card>
								<Card.Body>
									<Card.Title>{question}</Card.Title>
									<Card.Text>{answer}</Card.Text>
								</Card.Body>
							</Card>
						</li>
					);
				})}
			</ul>
			<Button href="/" variant="primary" className="my-3">
				Zur Startseite
			</Button>
		</>
	);
}
