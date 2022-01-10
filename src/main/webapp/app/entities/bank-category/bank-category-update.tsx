import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity, updateEntity, createEntity, reset } from './bank-category.reducer';
import { IBankCategory } from 'app/shared/model/bank-category.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const BankCategoryUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const bankCategoryEntity = useAppSelector(state => state.bankCategory.entity);
  const loading = useAppSelector(state => state.bankCategory.loading);
  const updating = useAppSelector(state => state.bankCategory.updating);
  const updateSuccess = useAppSelector(state => state.bankCategory.updateSuccess);
  const handleClose = () => {
    props.history.push('/bank-category');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...bankCategoryEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...bankCategoryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="akountsApp.bankCategory.home.createOrEditLabel" data-cy="BankCategoryCreateUpdateHeading">
            <Translate contentKey="akountsApp.bankCategory.home.createOrEditLabel">Create or edit a BankCategory</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="bank-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('akountsApp.bankCategory.parent')}
                id="bank-category-parent"
                name="parent"
                data-cy="parent"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankCategory.categorieLabel')}
                id="bank-category-categorieLabel"
                name="categorieLabel"
                data-cy="categorieLabel"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankCategory.categorieDesc')}
                id="bank-category-categorieDesc"
                name="categorieDesc"
                data-cy="categorieDesc"
                type="text"
              />
              <ValidatedField
                label={translate('akountsApp.bankCategory.income')}
                id="bank-category-income"
                name="income"
                data-cy="income"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('akountsApp.bankCategory.isexpense')}
                id="bank-category-isexpense"
                name="isexpense"
                data-cy="isexpense"
                check
                type="checkbox"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/bank-category" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default BankCategoryUpdate;
