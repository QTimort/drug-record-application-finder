import type {_GettersTree, StateTree, StoreDefinition} from "pinia";

export function defineInitializableStore<
  Id extends string,
  S extends StateTree = {},
  G extends _GettersTree<S> = {},
  A = {}
>(
  storeDefinition: StoreDefinition<Id, S, G, A>,
  initializer: (store: ReturnType<StoreDefinition<Id, S, G, A>>) => void
): StoreDefinition<Id, S, G, A> {
  const store = storeDefinition();
  initializer(store);
  return (() => store) as any;
}

