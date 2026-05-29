import { useEffect, useState } from "react";

const defaultCategories = ["flower", "water", "lava"];

// Factory functions for each monster type
function createFlowerMonster(monsterData) {
    return {
        id: monsterData.id,
        name: monsterData.name,
        type: "flower",
        imageUrl: monsterData.imageUrl || "https://example.com/flower.png",
    };
}

function createWaterMonster(monsterData) {
    return {
        id: monsterData.id,
        name: monsterData.name,
        type: "water",
        imageUrl: monsterData.imageUrl || "https://example.com/water.png",
    };
}

function createLavaMonster(monsterData) {
    return {
        id: monsterData.id,
        name: monsterData.name,
        type: "lava",
        imageUrl: monsterData.imageUrl || "https://example.com/lava.png",
    };
}

// Factory method selector
function createMonsterByType(type, monsterData) {
    const typeMap = {
        flower: createFlowerMonster,
        water: createWaterMonster,
        lava: createLavaMonster,
    };
    const factory = typeMap[type?.toLowerCase()] || createFlowerMonster;
    return factory(monsterData);
}

// Render monsters as HTML (accepts array of monsters)
function renderMonstersHtml(monsters) {
    if (!monsters || monsters.length === 0) {
        return "<html><div style='font-family:sans-serif;text-align:center;padding:20px;'><p>Selecciona un tipo de monstruo para verlo</p></div></html>";
    }

    const monsterCards = monsters.map((monster) => `
        <div style='display:inline-block;margin:10px;padding:16px;background:white;border:2px solid #ddd;border-radius:8px;text-align:center;width:280px;'>
            <h3>${monster.name}</h3>
            <p><strong>ID:</strong> ${monster.id}</p>
            <p><strong>Tipo:</strong> ${monster.type}</p>
            <img src='${monster.imageUrl}' width='200' height='200' alt='${monster.name}' style='border-radius:8px;margin-top:10px;'/>
        </div>
    `).join("");

    return `
        <html>
            <div style='font-family:sans-serif;padding:20px;'>
                <div style='text-align:center;margin-bottom:20px;'>
                    <p style='font-size:18px;color:#555;'>Se encontraron ${monsters.length} monstruo(s) de este tipo</p>
                </div>
                <div style='display:flex;flex-wrap:wrap;justify-content:center;gap:10px;'>
                    ${monsterCards}
                </div>
            </div>
        </html>`;
}

export default function Example() {
    const [monsters, setMonsters] = useState([]);
    const [error, setError] = useState(null);
    const [selectedType, setSelectedType] = useState(null);
    const [selectedMonstersHtml, setSelectedMonstersHtml] = useState("");

    useEffect(() => {
        const fetchMonsters = async () => {
            try {
                const query = `
                    query {
                        getAllMonsters {
                            id
                            name
                            monster
                            goreLevel
                            imageUrl
                        }
                    }
                `;
                const res = await fetch("http://localhost:8080/graphql", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ query }),
                });
                const data = await res.json();

                if (data.errors) {
                    setError(data.errors[0]?.message);
                    return;
                }

                setMonsters(data?.data?.getAllMonsters ?? []);
                setError(null);
            } catch (err) {
                console.log(err);
                setError("Error de conexión");
            }
        };

        fetchMonsters();
    }, []);

    const handleMonsterButtonClick = (type) => {
        const monstersOfType = monsters.filter((m) => m.monster?.toLowerCase() === type.toLowerCase());
        
        if (monstersOfType.length === 0) {
            setError(`No hay monstruos de tipo ${type}`);
            setSelectedType(null);
            setSelectedMonstersHtml("");
            return;
        }

        const createdMonsters = monstersOfType.map((monsterData) => createMonsterByType(type, monsterData));
        setSelectedType(type);
        setSelectedMonstersHtml(renderMonstersHtml(createdMonsters));
        setError(null);
    };

    const categories = monsters.reduce((grouped, monster) => {
        const category = monster.monster || "Sin categoría";
        grouped[category] = [...(grouped[category] ?? []), monster];
        return grouped;
    }, {});
    const visibleCategories = [
        ...defaultCategories,
        ...Object.keys(categories).filter((category) => !defaultCategories.includes(category)),
    ];

    return (
        <div style={{ padding: 20 }}>
            <h1>Monsters dinámicos</h1>

            {error && <p style={{ color: "red" }}>{error}</p>}

            {/* Factory Method Buttons */}
            <div style={{ marginBottom: 30, padding: 16, backgroundColor: "#e8f4f8", borderRadius: 8 }}>
                <h3>Crear monstruo usando Factory Method:</h3>
                <div style={{ display: "flex", gap: 12, flexWrap: "wrap" }}>
                    <button
                        onClick={() => handleMonsterButtonClick("flower")}
                        style={{
                            padding: "10px 20px",
                            fontSize: 16,
                            backgroundColor: selectedType === "flower" ? "#4CAF50" : "#f0f0f0",
                            color: selectedType === "flower" ? "white" : "black",
                            border: "2px solid #4CAF50",
                            borderRadius: 4,
                            cursor: "pointer",
                            fontWeight: selectedType === "flower" ? "bold" : "normal",
                        }}
                    >
                         Flower
                    </button>
                    <button
                        onClick={() => handleMonsterButtonClick("water")}
                        style={{
                            padding: "10px 20px",
                            fontSize: 16,
                            backgroundColor: selectedType === "water" ? "#2196F3" : "#f0f0f0",
                            color: selectedType === "water" ? "white" : "black",
                            border: "2px solid #2196F3",
                            borderRadius: 4,
                            cursor: "pointer",
                            fontWeight: selectedType === "water" ? "bold" : "normal",
                        }}
                    >
                         Water
                    </button>
                    <button
                        onClick={() => handleMonsterButtonClick("lava")}
                        style={{
                            padding: "10px 20px",
                            fontSize: 16,
                            backgroundColor: selectedType === "lava" ? "#FF5722" : "#f0f0f0",
                            color: selectedType === "lava" ? "white" : "black",
                            border: "2px solid #FF5722",
                            borderRadius: 4,
                            cursor: "pointer",
                            fontWeight: selectedType === "lava" ? "bold" : "normal",
                        }}
                    >
                         Lava
                    </button>
                </div>
            </div>

            {/* Display created monsters */}
            {selectedType && (
                <div style={{ marginBottom: 30, padding: 16, border: "2px solid #ddd", borderRadius: 8 }}>
                    <h3>Monstruos Creados ({selectedType.toUpperCase()}):</h3>
                    <div
                        style={{ marginTop: 16 }}
                        dangerouslySetInnerHTML={{ __html: selectedMonstersHtml }}
                    />
                </div>
            )}

            {/* All monsters by category */}
            <div style={{ marginTop: 40 }}>
                <h2>Todos los monstruos:</h2>
                {visibleCategories.map((category) => (
                    <section className="monster-category" key={category}>
                        <h3>{category}</h3>
                        <ul className="monster-list">
                            {(categories[category] ?? []).map((monster) => (
                                <li className="monster-card" key={monster.id}>
                                    {monster.imageUrl ? (
                                        <img
                                            className="monster-image"
                                            src={monster.imageUrl}
                                            alt={monster.name}
                                        />
                                    ) : (
                                        <div className="monster-image monster-image-empty">
                                            Sin imagen
                                        </div>
                                    )}
                                    <div className="monster-info">
                                        <p><strong>ID:</strong> {monster.id}</p>
                                        <p><strong>Nombre:</strong> {monster.name}</p>
                                        <p><strong>Gore level:</strong> {monster.goreLevel}</p>
                                        <p><strong>Tipo de monstruo:</strong> {monster.monster}</p>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </section>
                ))}
            </div>
        </div>
    );
}