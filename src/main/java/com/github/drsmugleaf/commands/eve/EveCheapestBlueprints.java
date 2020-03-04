package com.github.drsmugleaf.commands.eve;

import com.github.drsmugleaf.BanterBot4J;
import com.github.drsmugleaf.Nullable;
import com.github.drsmugleaf.commands.api.arguments.Argument;
import com.github.drsmugleaf.commands.api.Command;
import com.github.drsmugleaf.commands.api.CommandInfo;
import net.troja.eve.esi.ApiException;
import net.troja.eve.esi.api.ContractsApi;
import net.troja.eve.esi.api.UniverseApi;
import net.troja.eve.esi.model.PublicContractsItemsResponse;
import net.troja.eve.esi.model.PublicContractsResponse;
import net.troja.eve.esi.model.UniverseIdsResponse;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by DrSmugleaf on 26/08/2018
 */
@CommandInfo(
        description = "Get a list of the cheapest blueprints in Eve Online"
)
public class EveCheapestBlueprints extends Command {

    private static final ContractsApi CONTRACTS_API = new ContractsApi();
    private static final int THE_FORGE_REGION_ID = 10000002;
    private static final UniverseApi UNIVERSE_API = new UniverseApi();

    @Argument(position = 1, examples = "Erebus", maxWords = Integer.MAX_VALUE)
    private String blueprint;

    @Override
    public void run() {

        reply("Contacting the EVE Online API, please wait")
                .flatMap(message -> message.edit(spec -> {
                    List<PublicContractsResponse> contracts = getContracts(THE_FORGE_REGION_ID);
                    if (contracts.isEmpty()) {
                        spec.setContent("An Eve Online API error occurred retrieving contracts from The Forge");
                        return;
                    }

                    contracts.removeIf(contract -> !isItemExchangeContract(contract));
                    contracts.removeIf(contract -> !isSellingBlueprint(contract));
                    contracts.removeIf(contract -> !containsItem(contract, blueprint));

                    if (contracts.isEmpty()) {
                        spec.setContent("No contracts found selling a blueprint named " + blueprint);
                        return;
                    }

                    List<Double> prices = contracts
                            .stream()
                            .map(PublicContractsResponse::getPrice)
                            .sorted(Double::compareTo)
                            .collect(Collectors.toList());

                    DecimalFormat format = new DecimalFormat("#,###");
                    StringBuilder response = new StringBuilder("Cheapest prices:");
                    for (int i = 0; i < prices.size() || i >= 5; i++) {
                        Double price = prices.get(i);
                        response
                                .append("\n")
                                .append(format.format(price))
                                .append(" ISK");
                    }

                    spec.setContent(response.toString());
                }))
                .subscribe();
    }

    @Nullable
    private Integer getItemID(String name) {
        UniverseIdsResponse response;
        try {
            response = UNIVERSE_API.postUniverseIds(Collections.singletonList(name), null, null, null);
        } catch (ApiException e) {
            BanterBot4J.warn("Error contacting Eve Online's contract API", e);
            return null;
        }

        return response.getInventoryTypes().get(0).getId();
    }

    private List<PublicContractsResponse> getContracts(int regionID) {
        List<PublicContractsResponse> contracts;
        try {
            contracts = CONTRACTS_API.getContractsPublicRegionId(regionID, null, null, null);
        } catch (ApiException e) {
            BanterBot4J.warn("Error contacting Eve Online's contract API", e);
            return new ArrayList<>();
        }

        return contracts;
    }

    private boolean isItemExchangeContract(PublicContractsResponse contract) {
        return contract.getType() == PublicContractsResponse.TypeEnum.ITEM_EXCHANGE;
    }

    private boolean isSellingBlueprint(PublicContractsResponse contract) {
        if (contract.getVolume() > 1) {
            return false;
        }

        Integer contractID = contract.getContractId();
        List<PublicContractsItemsResponse> contractItems;
        try {
            contractItems = CONTRACTS_API.getContractsPublicItemsContractId(contractID, null, null, null);
        } catch (ApiException e) {
            BanterBot4J.warn("Error contacting Eve Online's contract API", e);
            return false;
        }

        if (contractItems == null) {
            return false;
        }

        for (PublicContractsItemsResponse item : contractItems) {
            if (item.getRuns() != null && item.getIsIncluded()) {
                return true;
            }
        }

        return false;
    }

    private boolean containsItem(PublicContractsResponse contract, String itemName) {
        Integer contractID = contract.getContractId();
        List<PublicContractsItemsResponse> contractItems;
        try {
            contractItems = CONTRACTS_API.getContractsPublicItemsContractId(contractID, null, null, null);
        } catch (ApiException e) {
            BanterBot4J.warn("Error contacting Eve Online's contract API", e);
            return false;
        }

        Integer itemID = getItemID(itemName);
        if (itemID == null) {
            return false;
        }

        for (PublicContractsItemsResponse item : contractItems) {
            if (item.getTypeId().equals(itemID)) {
                return true;
            }
        }

        return false;
    }

}
