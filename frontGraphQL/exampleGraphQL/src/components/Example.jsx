import { useEffect, useState } from "react";

export default function Example() {
    // Todos los monsters del backend
    const [monsters, setMonsters] = useState([]);
    // Tipos únicos extraídos de los datos
    const [types, setTypes] = useState([]);
    // Tipo seleccionado actualmente
    const [selectedType, setSelectedType] = useState(null);
    // Monsters filtrados según el tipo seleccionado
    const [filteredMonsters, setFilteredMonsters] = useState([]);
    // Estado para manejar errores (GraphQL o conexión)
    const [error, setError] = useState(null);

    // Carga todos los monsters al montar el componente
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

                const all = data?.data?.getAllMonsters ?? [];
                setMonsters(all);
                // Extrae tipos únicos para generar los botones
                setTypes([...new Set(all.map((m) => m.monster))]);
                setError(null);
            } catch (err) {
                console.log(err);
                setError("Error de conexión");
            }
        };
        fetchMonsters();
    }, []);


    useEffect(() => {
        if (!selectedType) {
            setFilteredMonsters([]);
            return;
        }

        setFilteredMonsters(monsters.filter((m) => m.monster === selectedType));
    }, [monsters, selectedType]);

    return (
        <div style={{ padding: 20 }}>
            <h1>Monsters dinámicos</h1>

            {error && <p style={{ color: "red" }}>{error}</p>}

            <div style={{ display: "flex", gap: 8, flexWrap: "wrap" }}>
                {types.map((type) => (
                    <button
                        key={type}
                        onClick={() => setSelectedType(selectedType === type ? null : type)}
                        style={{ fontWeight: selectedType === type ? "bold" : "normal" }}
                    >
                        {type}
                    </button>
                ))}
            </div>

            {selectedType && (
                <>
                    <h2>{selectedType}</h2>
                    <ul style={{ listStyle: "none", padding: 0 }}>
                        {filteredMonsters.map((m) => (
                            <li key={m.id}>
                                {m.name} | Gore Level: {m.goreLevel}
                            </li>
                        ))}
                    </ul>
                </>
            )}
        </div>
    );
}