Behaviour.register({
	'img.editAd': function(element) {
		setPointer(element);
		element.onclick = function() {
			self.location = pathPrefix + "/editAd?id=" + element.getAttribute("adId");
		}
	},
	
	'a.viewAd': function(a) {
		setPointer(a);
		a.onclick = function() {
			self.location = pathPrefix + "/viewAd?id=" + a.getAttribute("adId");
		}
	},
	
	'input.tradeType': function(element) {
		setPointer(element);
		element.onclick = function() {
			if(queryExec && !isEmpty(getValue("query(keywords)"))){
				element.form.submit();
			}else{
				var lastAds = "true" == getValue("lastAdsForTradeType");
				var advanced = "true" == getValue("advanced");
				self.location = pathPrefix + "/searchAds?lastAds=" + (lastAds) + "&" + encodeURIComponent("query(tradeType)")+"="+element.value;
			}
		}
	},
	
	'#backButton': function(element) {
		element.onclick = function() {
			var params = new QueryStringMap();
			if (booleanValue(params.get("fromQuickAccess"))) {
				self.location = pathPrefix + "/home";
			} else {
				history.back();
			}
		}
	},
	
	'img.removeAd': function(element) {
		setPointer(element);
		element.onclick = function() {
			if (confirm(removeConfirmationMessage)) {
				self.location = pathPrefix + "/removeAd?id=" + element.getAttribute("adId");
			}
		}
	},
	
	'.category': function(element) {
		setPointer(element);
		element.onclick = function() {
			var categoryId = element.getAttribute("categoryId");
			if (categoryId == "0") {
			    self.location = pathPrefix + "/searchAds?categoryOnly=true";
			} else {
				setValue("query(category)", categoryId);
				document.forms[0].submit();
			}
		}
	},
	
	'span.categoryLevel1Text': function(element) {
		changeClassOnHover(element, 'categoryLevel1Text', 'categoryLevel1TextHover');
	},
	
	'span.categoryLevel2Text': function(element) {
		changeClassOnHover(element, 'categoryLevel2Text', 'categoryLevel2TextHover');
	},
	
	'span.categoryLevel3Text': function(element) {
		changeClassOnHover(element, 'categoryLevel3Text', 'categoryLevel3TextHover');
	},
	
	'#modeButton': function(button) {
		button.onclick = function() {
			var advanced = "true" == getValue("advanced");
			self.location = self.location.pathname + "?advanced=" + (!advanced) + "&forceShowFields=true";
		}
	},
	'#searchButton': function(button) {
		button.onclick = function() {
			setValue("forceShowFields", "true");
		}
	},
	
	'#toggleFiltersButton': function(button) {
		button.onclick = function() {
			$('filtersDiv').toggle();
		}
	},
	'#viewLastAds': function(link) {
	    setPointer(link);
		link.onclick = function() {
			var index = (document.getElementsByName("query(tradeType)")[0].checked) ? 0 : 1;
			var radioSelection = document.getElementsByName("query(tradeType)")[index].value;
			self.location = pathPrefix + "/searchAds?lastAds=true&" + encodeURIComponent("query(tradeType)")+"="+radioSelection;
		}
	},
	'#viewCategories': function(link) {
	    setPointer(link);
		link.onclick = function() {
			self.location = pathPrefix + "/searchAds?categoryOnly=true";
		}
	},
	'img.print': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			printResults(form, pathPrefix + "/printAds");
		}
	},
	
	'img.exportCSV': function(img) {
		setPointer(img);
		img.onclick = function() {
			var form = document.forms[0];
			submitTo(form, pathPrefix + "/exportAdsToCsv");
		}
	}
});

Event.observe(self, "load", function() {
	try {
		getObject("query(keywords)").focus();
	} catch (e) {}
});