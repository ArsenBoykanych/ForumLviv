package aboikanych.forumlviv.ui.map;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import com.indoorway.android.common.sdk.listeners.GenericListenerArg1;
import com.indoorway.android.common.sdk.model.IndoorwayObjectParameters;
import com.indoorway.android.common.sdk.task.IndoorwayTask;
import com.indoorway.android.map.sdk.IndoorwayMapSdk;
import com.indoorway.android.map.sdk.listeners.OnMapLoadedListener;
import com.indoorway.android.map.sdk.listeners.OnObjectSelectedListener;
import com.indoorway.android.map.sdk.model.IndoorwayMap;
import com.indoorway.android.map.sdk.view.IndoorwayMapView;

import java.util.ArrayList;
import java.util.List;

import aboikanych.forumlviv.R;
import aboikanych.forumlviv.base.BaseFragment;
import aboikanych.forumlviv.net.model.ShopService;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenter;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServicePresenterImpl;
import aboikanych.forumlviv.ui.shops_services.mvp.ShopServiceView;
import aboikanych.forumlviv.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Boykanych on 05.06.2017.
 */

public class MapFragment extends BaseFragment implements ShopServiceView {

    public final static String ARG_MAP = "map_model";

    @BindView(R.id.mapView)
    IndoorwayMapView indoorwayMapView;
    @BindView(R.id.wayFinder)
    View wayView;
    @BindView(R.id.wayFAB)
    FloatingActionButton wayFAB;
    @BindView(R.id.wayClose)
    ImageView wayClose;

    @BindView(R.id.wayFrom)
    AutoCompleteTextView wayFrom;
    @BindView(R.id.wayTo)
    AutoCompleteTextView wayTo;

    @BindView(R.id.firstLevel)
    TextView firstLevel;

    @BindView(R.id.secondLevel)
    TextView secondLevel;

    @BindView(R.id.thirdLevel)
    TextView thirdLevel;

    private DialogFragment bottomSheetDialogFragment;
    private ShopServicePresenter presenter;
    private List<ShopService> shopServices;
    private List<ShopService> ieList, ieListTo;

    private List<IndoorwayObjectParameters> firstLevelObjects;
    private List<IndoorwayObjectParameters> secondLevelObjects;
    private List<IndoorwayObjectParameters> thirdLevelObjects;
    private int selectedLevel = 1, multiLevelNext, multiLevelFrom;
    private boolean multiLevelWay;
    private IndoorwayObjectParameters multiLift;
    private IndoorwayObjectParameters multiTo;
    private IndoorwayObjectParameters multiFrom;
    private boolean firstShopView;
    private IndoorwayObjectParameters needToSelect;

    public static MapFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putString(ARG_MAP, title);
        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ShopServicePresenterImpl();
        presenter.attachView(this);
        presenter.getShopsServices();
    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.hideSoftKeyboard(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.bind(this, view);

        IndoorwayMapSdk mapSdk = IndoorwayMapSdk.getInstance();
        indoorwayMapView.getCameraControl().setMapRotation(-90);
        indoorwayMapView.getCameraControl().setScale(0.008f);
        mapSdk.getConfig().setIconSize(8);

        indoorwayMapView.getSelectionControl()
                .setOnObjectSelectedListener(new OnObjectSelectedListener() {

                    @Override
                    public boolean canObjectBeSelected(IndoorwayObjectParameters parameters) {
                        // return true if object with given parameters can be selected
                        return !"inaccessible".equals(parameters.getType());
                    }

                    @Override
                    public void onObjectSelected(IndoorwayObjectParameters parameters) {
                        // POI Selected
                        if (!firstShopView) {
                            openSheetFragment(parameters);
                        }
                        firstShopView = false;
                    }

                    @Override
                    public void onSelectionCleared() {
                        // called when no object is selected
                    }
                });

        initMapObjects();
        if (getArguments() == null) {
            wayFAB.setVisibility(View.VISIBLE);
            indoorwayMapView.loadMap("HG7hpkd1FFw", "3RLhAnntR34");
            changeColorSelected(firstLevel);
        }

/*        final MarkersLayer myLayer = indoorwayMapView.getMarkerControl().addLayer();
        indoorwayMapView.getTouchControl().setOnTouchListener(new OnTouchListener() {
            @Override
            public void onTouch(Coordinates coordinates) {
                myLayer.remove("from");
                    myLayer.add(new DrawableCircle("from", 1.4f, ContextCompat.getColor(getActivity(),R.color.blue), Color.WHITE, 0.1f, coordinates));
            }
        });*/
        return view;
    }

    private void initMapObjects() {
        IndoorwayMapSdk.getInstance().getBuildingsApi()
                .getMapObjects("HG7hpkd1FFw", "3RLhAnntR34")
                .setOnCompletedListener(new GenericListenerArg1<IndoorwayMap>() {
                    @Override
                    public void onAction(IndoorwayMap indoorwayMap) {
                        firstLevelObjects = indoorwayMap.getObjects();

                        if (getArguments() != null) {
                            String title = getArguments().getString(ARG_MAP);
                            IndoorwayObjectParameters first = getFirstLevelShopByTitle(title);
                            if (first != null) {
                                needToSelect = first;
                                selectFirst();
                                firstShopView = true;
                            }
                        }
                    }
                })
                .setOnFailedListener(new GenericListenerArg1<IndoorwayTask.ProcessingException>() {
                    @Override
                    public void onAction(IndoorwayTask.ProcessingException e) {
                        // handle error, original exception is given on e.getCause()
                    }
                })
                .execute();

        IndoorwayMapSdk.getInstance().getBuildingsApi()
                .getMapObjects("HG7hpkd1FFw", "BTrK2zjfVQE")
                .setOnCompletedListener(new GenericListenerArg1<IndoorwayMap>() {
                    @Override
                    public void onAction(IndoorwayMap indoorwayMap) {
                        secondLevelObjects = indoorwayMap.getObjects();

                        if (getArguments() != null) {
                            String title = getArguments().getString(ARG_MAP);

                            IndoorwayObjectParameters second = getSecondLevelShopByTitle(title);
                            if (second != null) {
                                needToSelect = second;
                                selectSecond();
                                firstShopView = true;
                            }
                        }
                    }
                })
                .setOnFailedListener(new GenericListenerArg1<IndoorwayTask.ProcessingException>() {
                    @Override
                    public void onAction(IndoorwayTask.ProcessingException e) {
                        // handle error, original exception is given on e.getCause()
                    }
                })
                .execute();

        IndoorwayMapSdk.getInstance().getBuildingsApi()
                .getMapObjects("HG7hpkd1FFw", "tMZBAmLzAfM")
                .setOnCompletedListener(new GenericListenerArg1<IndoorwayMap>() {
                    @Override
                    public void onAction(IndoorwayMap indoorwayMap) {
                        thirdLevelObjects = indoorwayMap.getObjects();

                        if (getArguments() != null) {
                            String title = getArguments().getString(ARG_MAP);
                            IndoorwayObjectParameters third = getThirdLevelShopByTitle(title);
                            if (third != null) {
                                needToSelect = third;
                                selectThird();
                                firstShopView = true;
                            }
                        }
                    }
                })
                .setOnFailedListener(new GenericListenerArg1<IndoorwayTask.ProcessingException>() {
                    @Override
                    public void onAction(IndoorwayTask.ProcessingException e) {
                        // handle error, original exception is given on e.getCause()
                    }
                })
                .execute();
    }

    private void openSheetFragment(IndoorwayObjectParameters parameters) {
        if (shopServices != null) {
            for (ShopService item : shopServices) {
                if (item.getTitle().toLowerCase().equals(parameters.getName().toLowerCase())) {
                    bottomSheetDialogFragment =
                            MapBottomSheet.newInstance(item);
                    bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
                    break;
                }
            }
        }
    }

    @Override
    public void onShopsServicesLoaded(final List<ShopService> shopsServices) {
        this.shopServices = shopsServices;

        List<String> shopsList = new ArrayList<>();
        for (ShopService item : shopsServices) {
            shopsList.add(item.getTitle());
        }
        final AutoCompleteShopAdapter adapter = new AutoCompleteShopAdapter(getActivity(), shopsServices);

        ieList = new ArrayList<>();
        ieList.add(new ShopService("Моє місцеположення"));
        ieList.addAll(Utils.getIEElement());

        ieListTo = new ArrayList<>();
        ieListTo.addAll(Utils.getIEElement());

        wayFrom.setAdapter(new AutoCompleteShopAdapter(getActivity(), ieList));
        wayTo.setAdapter(new AutoCompleteShopAdapter(getActivity(), ieList));

        wayFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && !wayFrom.getAdapter().equals(adapter)) {
                    wayFrom.setAdapter(adapter);
                } else {
                    wayFrom.setAdapter(new AutoCompleteShopAdapter(getActivity(), ieList));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        wayTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 0 && !wayTo.getAdapter().equals(adapter)) {
                    wayTo.setAdapter(adapter);
                } else {
                    wayTo.setAdapter(new AutoCompleteShopAdapter(getActivity(), ieListTo));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @OnClick(value = {R.id.firstLevel, R.id.secondLevel, R.id.thirdLevel, R.id.wayFAB, R.id.wayClose, R.id.wayGo})
    public void onLevelsClick(View view) {
        switch (view.getId()) {
            case R.id.firstLevel:
                selectFirst();
                if (!multiLevelWay) {
                    indoorwayMapView.getNavigationControl().stop();
                } else if (multiLevelNext == selectedLevel) {
                    getDirections(multiLift, multiTo);
                } else if (multiLevelFrom == selectedLevel) {
                    getDirections(multiFrom, multiLift);
                }
                indoorwayMapView.getSelectionControl().deselect();

                break;
            case R.id.secondLevel:
                selectSecond();
                if (!multiLevelWay) {
                    indoorwayMapView.getNavigationControl().stop();
                } else if (multiLevelNext == selectedLevel) {
                    getDirections(multiLift, multiTo);
                } else if (multiLevelFrom == selectedLevel) {
                    getDirections(multiFrom, multiLift);
                }
                indoorwayMapView.getSelectionControl().deselect();
                break;
            case R.id.thirdLevel:
                selectThird();
                if (!multiLevelWay) {
                    indoorwayMapView.getNavigationControl().stop();
                } else if (multiLevelNext == selectedLevel) {
                    getDirections(multiLift, multiTo);
                } else if (multiLevelFrom == selectedLevel) {
                    getDirections(multiFrom, multiLift);
                }
                indoorwayMapView.getSelectionControl().deselect();
                break;
            case R.id.wayFAB:
                wayView.setVisibility(View.VISIBLE);
                wayFAB.setVisibility(View.GONE);
                break;
            case R.id.wayClose:
                closeWayFinder();
                break;
            case R.id.wayGo:
                // TODO GO
                indoorwayMapView.getNavigationControl().stop();
                Utils.hideSoftKeyboard(getActivity());

                String wayToString = wayTo.getText().toString();
                String wayFromString = wayFrom.getText().toString();
                if (wayFromString.equals("Моє місцеположення")
                        || wayToString.equals("Моє місцеположення")) {
                    if (!Utils.isInMall()) {
                        showToast("Щоб визначити ваше місцеположення зайдіть у торговий центр");
                    }
                } else if (!wayFromString.equals("Моє місцеположення")
                        && !wayToString.equals("Моє місцеположення")) {
                    Utils.hideSoftKeyboard(getActivity());

                    IndoorwayObjectParameters firstFrom = getFirstLevelShopByTitle(wayFromString);
                    IndoorwayObjectParameters firstTo = getFirstLevelShopByTitle(wayToString);

                    IndoorwayObjectParameters secondFrom = getSecondLevelShopByTitle(wayFromString);
                    IndoorwayObjectParameters secondTo = getSecondLevelShopByTitle(wayToString);

                    IndoorwayObjectParameters thirdFrom = getThirdLevelShopByTitle(wayFromString);
                    IndoorwayObjectParameters thirdTo = getThirdLevelShopByTitle(wayToString);

                    if (firstFrom != null && firstTo != null && selectedLevel == 1) {
                        getDirections(firstFrom, firstTo);
                        break;
                    } else if (secondFrom != null && secondTo != null && selectedLevel == 2) {
                        getDirections(secondFrom, secondTo);
                        break;
                    } else if (thirdFrom != null && thirdTo != null && selectedLevel == 3) {
                        getDirections(thirdFrom, thirdTo);
                        break;
                    }

                    multiLevelWay = true;
                    if (firstFrom != null) {
                        multiLevelFrom = 1;
                        if (selectedLevel != multiLevelFrom) {
                            selectFirst();
                        }
                        multiFrom = firstFrom;
                        multiLift = getFirstLevelShopByTitle("Ліфт");
                        if (secondTo != null) {
                            multiLevelNext = 2;
                            multiTo = secondTo;
                            showToast("Шлях передбачає використання ліфту до другого поверху, продовжуйте після зміни поверху");
                        } else {
                            multiLevelNext = 3;
                            multiTo = thirdTo;
                            showToast("Шлях передбачає використання ліфту до третього поверху, продовжуйте після зміни поверху");
                        }
                        getDirections(firstFrom, multiLift);
                    } else if (secondFrom != null) {
                        multiLevelFrom = 2;
                        if (selectedLevel != multiLevelFrom) {
                            selectSecond();
                        }
                        multiFrom = secondFrom;
                        multiLift = getSecondLevelShopByTitle("Ліфт");
                        if (firstTo != null) {
                            multiLevelNext = 1;
                            multiTo = firstTo;
                            showToast("Шлях передбачає використання ліфту до першого поверху, продовжуйте після зміни поверху");
                        } else {
                            multiLevelNext = 3;
                            multiTo = thirdTo;
                            showToast("Шлях передбачає використання ліфту до третього поверху, продовжуйте після зміни поверху");
                        }
                        getDirections(secondFrom, multiLift);
                    } else if (thirdFrom != null) {
                        multiLevelFrom = 3;
                        if (selectedLevel != multiLevelFrom) {
                            selectThird();
                        }
                        multiFrom = thirdFrom;
                        multiLift = getThirdLevelShopByTitle("Ліфт");
                        if (firstTo != null) {
                            multiLevelNext = 1;
                            multiTo = firstTo;
                            showToast("Шлях передбачає використання ліфту до першого поверху, продовжуйте після зміни поверху");
                        } else {
                            multiLevelNext = 2;
                            multiTo = secondTo;
                            showToast("Шлях передбачає використання ліфту до другого поверху, продовжуйте після зміни поверху");
                        }
                        getDirections(thirdFrom, multiLift);
                    }
                } else {
                    showToast("Неможливо визначити шлях");
                }
                break;
        }
    }

    private void selectThird() {
        selectedLevel = 3;
        indoorwayMapView.loadMap("HG7hpkd1FFw", "tMZBAmLzAfM");
        showToast("Третій поверх");
        changeColorSelected(thirdLevel);
        changeColorUnSelected(secondLevel);
        changeColorUnSelected(firstLevel);
        if (getArguments() != null && needToSelect != null) {
            indoorwayMapView.setOnMapLoadCompletedListener(new OnMapLoadedListener() {
                @Override
                public void onAction(IndoorwayMap indoorwayMap) {
                    indoorwayMapView.getCameraControl().setPosition(needToSelect.getCoordinates());
                    indoorwayMapView.getCameraControl().setScale(0.02f);
                    indoorwayMapView.getSelectionControl().selectObject(needToSelect.getId());
                }
            });
        }
    }

    private void selectSecond() {
        selectedLevel = 2;
        indoorwayMapView.loadMap("HG7hpkd1FFw", "BTrK2zjfVQE");
        showToast("Другий поверх");
        changeColorSelected(secondLevel);
        changeColorUnSelected(firstLevel);
        changeColorUnSelected(thirdLevel);
        if (getArguments() != null && needToSelect != null) {
            indoorwayMapView.setOnMapLoadCompletedListener(new OnMapLoadedListener() {
                @Override
                public void onAction(IndoorwayMap indoorwayMap) {
                    indoorwayMapView.getCameraControl().setPosition(needToSelect.getCoordinates());
                    indoorwayMapView.getCameraControl().setScale(0.02f);
                    indoorwayMapView.getSelectionControl().selectObject(needToSelect.getId());
                }
            });
        }
    }

    private void selectFirst() {
        selectedLevel = 1;
        indoorwayMapView.loadMap("HG7hpkd1FFw", "3RLhAnntR34");
        showToast("Перший поверх");
        changeColorSelected(firstLevel);
        changeColorUnSelected(secondLevel);
        changeColorUnSelected(thirdLevel);
        if (getArguments() != null && needToSelect != null) {
            indoorwayMapView.setOnMapLoadCompletedListener(new OnMapLoadedListener() {
                @Override
                public void onAction(IndoorwayMap indoorwayMap) {
                    indoorwayMapView.getCameraControl().setPosition(needToSelect.getCoordinates());
                    indoorwayMapView.getCameraControl().setScale(0.02f);
                    indoorwayMapView.getSelectionControl().selectObject(needToSelect.getId());
                }
            });
        }
    }

    private void getDirections(IndoorwayObjectParameters firstFrom, IndoorwayObjectParameters firstTo) {
        indoorwayMapView.getNavigationControl()
                .start(firstFrom.getCoordinates(), firstTo.getCoordinates());
        indoorwayMapView.getCameraControl().setPosition(firstFrom.getCoordinates());
        indoorwayMapView.getCameraControl().setScale(0.04f);
    }

    private void changeColorSelected(TextView textView) {
        textView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.levels));
    }

    private void changeColorUnSelected(TextView textView) {
        textView.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.bg_white_border));
        textView.setTextColor(ContextCompat.getColor(getActivity(), R.color.white));
    }

    private void closeWayFinder() {
        wayView.setVisibility(View.GONE);
        wayFrom.setText("");
        wayFrom.clearFocus();
        wayTo.setText("");
        wayTo.clearFocus();
        wayFAB.setVisibility(View.VISIBLE);
        Utils.hideSoftKeyboard(getActivity());
        indoorwayMapView.getNavigationControl().stop();
        multiLevelWay = false;
        multiLevelNext = -1;
        multiLevelFrom = -1;
    }

    public IndoorwayObjectParameters getFirstLevelShopByTitle(String title) {
        for (IndoorwayObjectParameters item : firstLevelObjects) {
            if (item.getName().toLowerCase().equals(title.toLowerCase()))
                return item;
        }
        return null;
    }

    public IndoorwayObjectParameters getSecondLevelShopByTitle(String title) {
        for (IndoorwayObjectParameters item : secondLevelObjects) {
            if (item.getName().toLowerCase().equals(title.toLowerCase()))
                return item;
        }
        return null;
    }

    public IndoorwayObjectParameters getThirdLevelShopByTitle(String title) {
        for (IndoorwayObjectParameters item : thirdLevelObjects) {
            if (item.getName().toLowerCase().equals(title.toLowerCase()))
                return item;
        }
        return null;
    }
}
