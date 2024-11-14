
    document.addEventListener("DOMContentLoaded", () => {  // Wartet darauf, dass der gesamte Inhalt des Dokuments geladen ist, bevor der Code ausgeführt wird
        const playfield = document.querySelector('.playfield'); // Referenz zum Spielfeld
        const cells = []; // Array, um alle Zellen des Spielfelds zu speichern
        let mineCount = 10; //Default Anzahl von Minen
        let isGameOver = false; // Status des Spiels (true, wenn das Spiel vorbei ist)
        let size = 9; // Default grid (9x9)
    
        // Erstellt Spielfeld
        function createBoard() {
            playfield.innerHTML = ''; // Cleart Spielfeld
            cells.length = 0; // Cleart den Cells Array
            playfield.style.gridTemplateColumns = `repeat(${size}, 1fr)`; // Setzt die Anzahl der Spalten im Grid
            playfield.style.gridTemplateRows = `repeat(${size}, 1fr)`; // Setzt die Anzahl der Reihen im Grid
            const cellSize = playfield.clientWidth / size; // Berechnet Cellsize
            for (let i = 0; i < size * size; i++) {
                const cell = document.createElement('div');
                cell.classList.add('cell');
                cell.style.width = `${cellSize}px`; // Set cell width
                cell.style.height = `${cellSize}px`; // Set cell height
                cell.setAttribute('id', i);
                cell.addEventListener('click', () => clickCell(cell));
    
                cell.addEventListener('contextmenu', (e) => {
                    e.preventDefault(); // verhindet standard context menu
                    addFlag(cell); // fügt flage oder entfernt sie
                });  

                playfield.appendChild(cell); // Fügt die Zelle dem Spielfeld hinzu
                cells.push(cell); // Fügt die Zelle dem cells-Array hinzu
            }
            addMines(); // Fügt die Minen zum Spielfeld hinzu
        }

    // Minenrandomizer
    function addMines() {
        let placedMines = 0; // Zähler für platzierte Minen
        while (placedMines < mineCount) {
            const randomIndex = Math.floor(Math.random() * cells.length); // Wählt einen zufälligen Index für die Mine
            if (!cells[randomIndex].classList.contains('mine')) { // Überprüft, ob an dieser Stelle bereits eine Mine ist
                cells[randomIndex].classList.add('mine-hidden'); // Fügt die Klasse für versteckte Minen hinzu
                placedMines++; // Erhöht den Zähler für platzierte Minen
            }
        }
        addNumbers(); // Fügt Zahlen zu den Zellen hinzu, die anzeigen, wie viele Minen in der Nähe sind
    }

    // Zahlen für die Felder, um zu zeigen wv Minen in der Nähe sind
    function addNumbers() {
        for (let i = 0; i < cells.length; i++) {
            let total = 0; // Zähler für angrenzende Minen
            const isLeftEdge = (i % size === 0); // Überprüft, ob die Zelle am linken Rand des Spielfelds ist
            const isRightEdge = (i % size === size - 1); // Überprüft, ob die Zelle am rechten Rand des Spielfelds ist

            if (cells[i].classList.contains('mine-hidden')) { // Wenn die Zelle eine Mine ist, überspringe sie
                continue;
            }
            // Überprüft die umliegenden Zellen auf Minen und erhöht den Zähler
            if (i > 0 && !isLeftEdge && cells[i - 1].classList.contains('mine-hidden')) total++;
            if (i > size - 1 && !isRightEdge && cells[i + 1 - size].classList.contains('mine-hidden')) total++;
            if (i >= size && cells[i - size].classList.contains('mine-hidden')) total++;
            if (i >= size + 1 && !isLeftEdge && cells[i - 1 - size].classList.contains('mine-hidden')) total++;
            if (i < size * size - 1 && !isRightEdge && cells[i + 1].classList.contains('mine-hidden')) total++;
            if (i < size * (size - 1) && !isLeftEdge && cells[i - 1 + size].classList.contains('mine-hidden')) total++;
            if (i < size * (size - 1) - 1 && !isRightEdge && cells[i + 1 + size].classList.contains('mine-hidden')) total++;
            if (i < size * (size - 1) && cells[i + size].classList.contains('mine-hidden')) total++;

            cells[i].setAttribute('data', total); // Setzt das Attribut 'data' auf den Wert des Zählers
        }
        if (total == 1){          
        }
    }
    // Funktion, die ausgeführt wird, wenn eine Zelle geklickt wird
    function clickCell(cell) {
        if (isGameOver) return; // Wenn das Spiel vorbei ist, tue nichts
        if (cell.classList.contains('revealed') || cell.classList.contains('flag')) return; // Wenn die Zelle bereits aufgedeckt oder markiert ist, tue nichts
    
    
        if (cell.classList.contains('mine-hidden')) { // Wenn die Zelle eine Mine ist
            cell.classList.add('mine'); // Zeigt die Mine
            cell.classList.remove('mine-hidden'); // Entfernt die Klasse für versteckte Minen
            gameOver(); // Beendet das Spiel
        } else {
            let total = cell.getAttribute('data'); // Holt den Wert des 'data'-Attributs
            cell.classList.add('revealed'); // Zeigt die Zelle an
            if (total == 1) {
                cell.classList.add('revealed', 'one'); // Fügt die Klasse für eine Zelle mit einer angrenzenden Mine hinzu
            } else if (total == 2) {
                cell.classList.add('revealed', 'two');
            } else if (total == 3) {
                cell.classList.add('revealed', 'three');
            } else if (total == 4) {
                cell.classList.add('revealed', 'four');
            } else if (total == 5) {
                cell.classList.add('revealed', 'five');
            } else if (total == 6) {
                cell.classList.add('revealed', 'six');
            } else if (total == 7) {
                cell.classList.add('revealed', 'seven');
            } else if (total == 8) {
                cell.classList.add('revealed', 'eight');
            } else {
                revealCells(cell); // Wenn total 0 ist, rekursiv benachbarte Zellen aufdecken
            }
        }
    }

    // zeigt leere Felder 
    function revealCells(cell) {
        const id = parseInt(cell.id); // Holt die ID der Zelle
        if (cell.classList.contains('revealed')) return; // Wenn die Zelle bereits aufgedeckt ist, tue nichts
        cell.classList.add('revealed'); // Zeigt die Zelle an

        const isLeftEdge = (id % size === 0); // Überprüft, ob die Zelle am linken Rand des Spielfelds ist
        const isRightEdge = (id % size === size - 1); // Überprüft, ob die Zelle am rechten Rand des Spielfelds ist

        setTimeout(() => {
            if (id > 0 && !isLeftEdge) clickCell(cells[id - 1]); // Überprüft und deckt die Zelle links auf
            if (id > size - 1 && !isRightEdge) clickCell(cells[id + 1 - size]); // Überprüft und deckt die Zelle oben rechts auf
            if (id >= size) clickCell(cells[id - size]); // Überprüft und deckt die Zelle oben auf
            if (id >= size + 1 && !isLeftEdge) clickCell(cells[id - 1 - size]); // Überprüft und deckt die Zelle oben links auf
            if (id < size * size - 1 && !isRightEdge) clickCell(cells[id + 1]); // Überprüft und deckt die Zelle rechts auf
            if (id < size * (size - 1) && !isLeftEdge) clickCell(cells[id - 1 + size]); // Überprüft und deckt die Zelle unten links auf
            if (id < size * (size - 1) - 1 && !isRightEdge) clickCell(cells[id + 1 + size]); // Überprüft und deckt die Zelle unten rechts auf
            if (id < size * (size - 1)) clickCell(cells[id + size]); // Überprüft und deckt die Zelle unten auf
        }, 10); // Verzögert die Ausführung um 10ms für eine visuelle Darstellung
    }
    // Funktion zum Hinzufügen oder Entfernen einer Flagge
    function addFlag(cell) {
        if (isGameOver) return; // Wenn das Spiel vorbei ist, tue nichts
        if (!cell.classList.contains('revealed') && !cell.classList.contains('flag')) {
            cell.classList.add('flag'); // Fügt eine Flagge hinzu
        } else if (cell.classList.contains('flag')) {
            cell.classList.remove('flag'); // Entfernt die Flagge
        }
    }

    // Du hast verloren bro
    function gameOver() {
        isGameOver = true; // Setzt den Spielstatus auf beendet
        cells.forEach(cell => {
            if (cell.classList.contains('mine-hidden')) {
                cell.classList.add('revealed', 'mine'); // Zeigt alle versteckten Minen
                cell.classList.remove('mine-hidden');  // Entfernt die Klasse für versteckte Minen
            }
            if (cell.classList.contains('flag')) {
                cell.classList.remove('flag'); // Entfernt alle Flaggen
            }
        });
        alert('Game Over! You hit a mine!'); // Zeigt eine Game Over Nachricht an
    }

    // Schwierigkeitsgrade
    document.querySelector('.footer-button:nth-child(1)').addEventListener('click', () => {
        size = 9; // Setzt die Größe des Spielfelds auf 9x9
        mineCount = 10; // Setzt die Anzahl der Minen auf 10
        isGameOver = false; // Setzt den Spielstatus auf nicht beendet
        createBoard(); // Erstellt das Spielfeld neu
    });

    document.querySelector('.footer-button:nth-child(2)').addEventListener('click', () => {
        size = 16;
        mineCount = 40;
        isGameOver = false;
        createBoard();
    });

    document.querySelector('.footer-button:nth-child(3)').addEventListener('click', () => {
        size = 24;
        mineCount = 99;
        isGameOver = false;
        createBoard();
    });

    createBoard();
});
