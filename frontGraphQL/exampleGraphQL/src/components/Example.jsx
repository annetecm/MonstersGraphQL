import { useEffect, useState } from "react";

const defaultCategories = ["flower", "water", "lava"];

export default function Example() {
    const [monsters, setMonsters] = useState([]);
    const [error, setError] = useState(null);

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

            {visibleCategories.map((category) => (
                <section className="monster-category" key={category}>
                    <h2>{category}</h2>
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
    );
}
