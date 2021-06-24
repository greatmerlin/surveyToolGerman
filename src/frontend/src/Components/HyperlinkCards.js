import React from "react";
import {
  CardDeck,
  Card,
  InputGroup,
  Button,
  FormControl,
} from "react-bootstrap";

/**
 * Cards showing the link for participation and evaluation.
 * Includes the function to copy to clipboard with a click on a button.
 *
 * @param {participationLink} uuid, needed to have access to the participation page.
 * @param {evaluationLink} uuid, needed to have access to the evaluation page.
 * @param {className} string
 * @param {showToast} function, to display a message to the user.
 * @returns JSX.Element
 */


export default function HyperlinkCards({
  participationLink,
  evaluationLink,
  className,
  showToast,
}) {
  const cards = [
    {
      id: "participate",
      title: "Teilnahmelink",
      text: "Lade Teilnehmer ein, indem du mit ihnen folgenden Link teilst:",
      url: `${window.location.origin}/participate/${participationLink}`,
      copyLabel: "Kopieren",
    },
    {
      id: "evaluate",
      title: "Auswertungslink",
      text:
        "Um zurück auf diese Auswertungsseite zu gelangen, solltest du dir diesen Link merken:",
      url: `${window.location.origin}/evaluate/${evaluationLink}`,
      copyLabel: "Kopieren",
    },
  ];

  const copyToClipboard = (id) => {
    const input = document.querySelector(`#${id}`);
    input.focus();
    input.select();

    try {
      const successful = document.execCommand("copy");
      if (successful) {
        showToast("Link wurde in deine Zwischenablage kopiert.", false);
      } else {
        showToast(
          "Beim Kopieren in die Zwischenablage ist ein Fehler aufgetreten."
        );
      }
    } catch (err) {
      const msg =
        "Beim Kopieren in die Zwischenablage ist ein Fehler aufgetreten.";
      console.error(msg, err);
      showToast(msg);
    }
  };

  return (
    <CardDeck className={className}>
      {cards.map((card, i) => (
        <Card key={i}>
          <Card.Body>
            <Card.Title>{card.title}</Card.Title>
            <Card.Text>{card.text}</Card.Text>
          </Card.Body>
          <Card.Footer>
            <InputGroup>
              <FormControl id={card.id} value={card.url} readOnly />
              <InputGroup.Append>
                <Button
                  onClick={() => copyToClipboard(card.id)}
                  variant="outline-primary"
                >
                  {card.copyLabel}
                </Button>
              </InputGroup.Append>
            </InputGroup>
          </Card.Footer>
        </Card>
      ))}
    </CardDeck>
  );
}
