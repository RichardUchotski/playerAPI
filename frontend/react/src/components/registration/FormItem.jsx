export default function FormItem({ children, inputType, id, placeholder, teams}) {
        let input;


        switch (inputType) {
            case "number":
                input = (
                    <div className={"form-item-container"}>
                        <label className={"form-item-label"} htmlFor={id}>{children}</label>
                       <input className={"form-item-input"} placeholder={placeholder} type="number" min={6} max={120} step={1} id={id} name={id} required={true} />
                    </div>
                );
                break;

            case "checkbox":
                input = (
                    <div className={"form-item-container"}>
                       <input className={"form-item-input checkbox"} placeholder={placeholder} type="checkbox"  id={id} name={id} required={true} />
                        <label className={"form-item-label checkbox"} htmlFor={id}>{children}</label>
                    </div>
                );
                break;

            case "select-gender":
                input = (
                    <div className={"form-item-container"}>
                        <label className={"form-item-label"} htmlFor={id}>{children}</label>
                        <select id={id} name={id} defaultValue="" required={true}>
                            <option value="" disabled>
                                Please select a gender
                            </option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="nonbinary">Non-Binary</option>
                            <option value="gender-fluid">Gender Fluid</option>
                            <option value="preferNotToSay">Prefer not to say</option>
                        </select>
                    </div>
                );
                break;

            case "select-team":
                input = (
                    <div className={"form-item-container"}>
                        <label className={"form-item-label"} htmlFor={id}>{children}</label>
                        <select id={id} name={id} defaultValue="" required={true}>
                            <option value="" disabled>Please select a team</option>
                                {teams.map((team, index) => (
                                    <option key={index} value={team.teamName}>{team.teamName}</option>
                                ))}
                        </select>
                    </div>
                );
                break;

            default:
                input = (
                    <div className={"form-item-container"}>
                        <label className={"form-item-label"} htmlFor={id}>{children}</label>
                       <input className={"form-item-input"} placeholder={placeholder} type={inputType} id={id} name={id}  required={true}/>
                    </div>
                );
                break;
        }

        return input;
}