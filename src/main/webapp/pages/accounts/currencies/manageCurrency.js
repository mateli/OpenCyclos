function isInsert() {
	var id = parseInt(getValue("currencyId"));
	return (isNaN(id) || id == 0);
}

